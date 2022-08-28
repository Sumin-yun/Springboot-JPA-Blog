package com.cos.blog.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할수있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp 허용


@Controller
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Value("${cos.key}")
  private String cosKey;

  @Autowired
  private UserService userService;

  @GetMapping("/auth/joinForm")
  public String joinForm() {
    return "user/joinForm";
  }

  @GetMapping("/auth/loginForm")
  public String loginForm() {
    return "user/loginForm";
  }

  @GetMapping("/auth/kakao/callback")
  public String kakoCallback(String code) {

    RestTemplate rt = new RestTemplate();           //이전 httpUrlConnection 사용
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    //post 방식으로 데이터 요청
    params.add("grant_type", "authorization_code");
    params.add("client_id", "a0fd80bdda5c8a530f845ecc671be271");
    params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
    params.add("code", code);

    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
        new HttpEntity<>(params, headers);

    ResponseEntity<String> response = rt.exchange(
        "https://kauth.kakao.com/oauth/token",
        HttpMethod.POST,
        kakaoTokenRequest,
        String.class
        );

    ObjectMapper objectMapper = new ObjectMapper();
    OAuthToken oauthToken = null;

    try {
      oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.println("카카오 엑세스 토큰" + oauthToken.getAccess_token());

    RestTemplate rt2 = new RestTemplate();           //이전 httpUrlConnection 사용
    HttpHeaders headers2= new HttpHeaders();
    headers2.add("Authorization", "Bearer " +oauthToken.getAccess_token());
    headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
        new HttpEntity<>(headers2);

    ResponseEntity<String> response2 = rt2.exchange(
        "https://kapi.kakao.com/v2/user/me",
        HttpMethod.POST,
        kakaoProfileRequest2,
        String.class
        );

    ObjectMapper objectMapper2 = new ObjectMapper();
    KakaoProfile kakaoProfile = null;

    try {
      kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println("카카오아이디"+kakaoProfile.getId());
    System.out.println("카카오이메일"+kakaoProfile.getKakao_account().getEmail());

    System.out.println("카카오 유저네임" + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
    System.out.println("블로그 이메일" +  kakaoProfile.getKakao_account().getEmail());
    //uuid -> 값이 계속 변경됨
    UUID garbagePassword = UUID.randomUUID();
    System.out.println("========="+cosKey);

    User kakaoUser = User.builder()
        .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
        .password(cosKey)
        .email(kakaoProfile.getKakao_account().getEmail())
        .oauth("kakao")
        .build();

    //가입자 미가입자 분기처리
    User originUser = userService.회원찾기(kakaoUser.getUsername());

    if(originUser.getUsername() == null) {
      System.out.println("===========기존회원이 아님");
      userService.회원가입(kakaoUser);
    }
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return "redirect:/";
  }

  @GetMapping("/user/updateForm")
  public String updateForm() {
    return "user/updateForm";
  }
}
