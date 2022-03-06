package com.cos.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Getter
//@Setter
//@RequiredArgsConstructor    //final붙은 객체 생성자 만듦


@Data                                   //Getter. Setter 자동생성
@AllArgsConstructor         //생성자 자동생성 
public class Member {
  private int id;
  private String userid;
  private String password;
  private String email;

}
