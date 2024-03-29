package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.cos.blog.model.User;
import lombok.Data;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 안료되면 userDetail 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션장소에 저장.
@Data
public class PrincipalDetail implements UserDetails{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private User user;              //콤포지션

  public PrincipalDetail(User user) {
    this.user = user;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  //계정 만료 여부 리턴 (true:만료안됨)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  //비밀번호 만료 여부 리턴
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //계정활성화 여부 리턴 (true:활성화)
  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collectors = new ArrayList<>();
    collectors.add(()->{return "ROLE_"+user.getRole();});

    return collectors;
  }


}
