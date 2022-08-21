package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록(IOC)
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Transactional
  public int 회원가입(User user) {
    try {
      String rawPassword = user.getPassword();
      String encPassword = encoder.encode(rawPassword);
      user.setPassword(encPassword);
      user.setRole(RoleType.USER);
      userRepository.save(user);
      return 1;
    } catch(Exception e) {
      e.printStackTrace();
      System.out.println("UserService: 회원가입()" +e.getMessage());
    }
    return -1;
  }

  @Transactional
  public void 회원수정(User user) {
    //수정시에는 영속성 컨텍스트 User 오브젝트 영속화 시키고, 영속화 된 User 오브젝트를 수정
    //select해서 영속화를 해준다.
    User persistance = userRepository.findById(user.getId()).orElseThrow(()-> {
      return new IllegalArgumentException("회원찾기 실패");
    });
    String rawPassword = user.getPassword();
    String encPassword = encoder.encode(rawPassword);
    persistance.setPassword(encPassword);
    persistance.setEmail(user.getEmail());
  }

}

//  @Transactional(readOnly = true)
//  public User 로그인(User user) {
//    return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//  }