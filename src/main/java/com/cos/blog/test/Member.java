package com.cos.blog.test;

public class Member {
  private int id;
  private String userid;
  private String password;
  private String email;


  public Member(int id, String userid, String password, String email) {
    this.id = id;
    this.userid = userid;
    this.password = password;
    this.email = email;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getUserid() {
    return userid;
  }
  public void setUserid(String userid) {
    this.userid = userid;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }


}
