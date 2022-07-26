package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

  @Autowired  //의존성주입(DI)
  private UserRepository userRepository;

  // http://localhost:8080/blog/dummy/join(요청)
  // http body에 useName, p.w, email을 가지고 (요청)
  @PostMapping("/dummy/join")
  public String join(User user) {
    System.out.println("usertName" + user.getId());
    System.out.println("usertName" + user.getUsername());
    System.out.println("password" + user.getPassword());
    System.out.println("email" + user.getEmail());
    System.out.println("email" + user.getRole());
    System.out.println("email" + user.getCreateDate());

    user.setRole(RoleType.User);
    userRepository.save(user);
    return "회원가입이 완료되었습니다";
  }
}
