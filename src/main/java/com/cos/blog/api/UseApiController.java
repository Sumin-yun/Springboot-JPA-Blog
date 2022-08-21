package com.cos.blog.api;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UseApiController {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/auth/joinProc")
  public ResponseDto<Integer> save(@RequestBody User user) {
    System.out.println("USER apiController : save 호출");
    int result = userService.회원가입(user);
    return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
  }

  @PutMapping("/user")
  public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal,  HttpSession session) {
    userService.회원수정(user);
    //트랜젝션 종료. db에 같은 값은 변경 됐으나 세션값변경안됨.
    //세션값을 직접 변경해줘야함

    //세션 등록
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
  }


}
