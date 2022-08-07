package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration                                                                       //Bean등록:스프링컨테이너에서 객체를 관리할 수 있게 하는 것(IOC)
@EnableWebSecurity                                                             //시큐리티 필터 추가(시큐리티 설정)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정 주소 접근 시 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  @Bean         //리턴값을 스프링이 관리 (IOC)
  public BCryptPasswordEncoder encodePWD() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
    .csrf().disable()       //csrf토큰 비활성화
    .authorizeRequests()
    .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**")
    .permitAll()
    .anyRequest()
    .authenticated()
    .and()
    .formLogin()
    .loginPage("/auth/loginForm");
  }
}
