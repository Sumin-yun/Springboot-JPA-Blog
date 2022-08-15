package com.cos.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UseApiController {

  @Autowired
  private UserService userService;

  @PostMapping("/auth/joinProc")
  public ResponseDto<Integer> save(@RequestBody User user) {
    System.out.println("USER apiController : save 호출");
    int result = userService.회원가입(user);
    return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
  }

}
