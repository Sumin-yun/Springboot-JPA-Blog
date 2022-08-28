package com.cos.blog.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  //빈생성자
@AllArgsConstructor //전체생성자
@Builder
//ORM -> java  Object->table로 변환해주는 기술
@Entity //User클래스가 변수를 읽어서 자동으로 mysql에 테이블 생성 
//@DynamicInsert        //insert 시 null인 컬럼은 제외하고  쿼리 실행
public class User {

  @Id  //PK..
  @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 넘버링전략을 따라감
  private int id;   //시퀀스, auto-increment

  @Column(nullable = false, length = 100)
  private String username;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 50)
  private String email;

  //@ColumnDefault(user)
  //DB는 RoleType이라는 것이 없다.
  @Enumerated(EnumType.STRING)
  private RoleType role;      //Enum(ADMIN, USER)

  private String oauth;

  @CreationTimestamp //시간 자동입력
  private Timestamp createDate;

}
