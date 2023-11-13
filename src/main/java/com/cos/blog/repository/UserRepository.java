package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// DAO
// 자동으로 bean등록이 된다.
// @repository // 생략 가능
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);
}

// JPA Naming 전략
// select * from user where username = ? and password = ?;
//    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "select * from user where username = ?1 and password = ?2", nativeQuery = true)
//    User login(String username, String password);
