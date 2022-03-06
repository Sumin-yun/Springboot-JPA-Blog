package com.cos.blog;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자 요청 -> html응답 
//   : @controller

//사용자가 요청 -> data 응답

@RestController
public class HttpControllerTest{

  private static final String TAG = "HttpControllerTest :";

  public String lombokTest() {
    Member m = new Member(1, "sss", "1234", "email");
    System.out.println(TAG + "getter:" + m.getId());
    m.setId(5000);
    System.out.println(TAG + "setter:" + m.getId());
    return "lombok test 완료";
  }

  // http://localhost:8080/http/get (select)
  @GetMapping("/http/get")
  public String getTest(Member m) {
    return "get 요청 : "+m.getId()+","+m.getUserid()+","+m.getEmail();
  }

  @PostMapping("/http/post")
  public String postTest(@RequestBody String text) {
    return "post 요청"+text;
  }

  @PutMapping("/http/put")
  public String putTest() {
    return "put 요청";
  }

  @DeleteMapping("/http/delete")
  public String deleteTest() {
    return "delete 요청";
  }

}
