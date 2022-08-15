package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cos.blog.config.auth.PrincipalDetailService;


@Configuration                                                                       //Bean등록:스프링컨테이너에서 객체를 관리할 수 있게 하는 것(IOC)
@EnableWebSecurity                                                             //시큐리티 필터 추가(시큐리티 설정)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정 주소 접근 시 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  @Autowired
  private PrincipalDetailService principalDetailService;

  @Bean         //리턴값을 스프링이 관리 (IOC)
  public BCryptPasswordEncoder encodePWD() {
    return new BCryptPasswordEncoder();
  }

  //시큐리티가 대신 로그인 시 pw가로채기 함
  //해당 pw가 뭘로 해쉬가 되어 회원가입 되었는지 알아야
  //db의 해쉬랑 비교할 수 있음.

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
  }



  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
    .csrf().disable()       //csrf토큰 비활성화
    .authorizeRequests()
    .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**","/dummy/**")
    .permitAll()
    .anyRequest()
    .authenticated()
    .and()
    .formLogin()
    .loginPage("/auth/loginForm")
    .loginProcessingUrl("/auth/loginProc")
    .defaultSuccessUrl("/");            //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인한다.
  }
}
