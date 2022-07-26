package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.blog.model.User;

//jpa레포는 user테이블의 integer형태를 pk 로 가진다.
//DAO와 비슷.
//자동으로 bean 등록이 된다. @repository 생략 가
public interface UserRepository extends JpaRepository<User, Integer>{  


}
