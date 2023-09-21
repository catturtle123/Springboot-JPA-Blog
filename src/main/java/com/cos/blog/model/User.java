package com.cos.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

// ORM -> 자바(다른언어) OBJECT -> 테이블로 매핑이 됨.
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략
    private int id;
    @Column(nullable = false,length = 30)
    private String username; // 아이디
    @Column(nullable = false,length = 100) // 123456 => 해쉬(비밀번호 암호화)
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @ColumnDefault("'user'")// ''을 붙여주어야함.
    private String role; // Enum을 쓰는 게 좋다.  // admin, user, manager => 도메인 설정 가능 (범위 설정 -> 오타가 나도 막을 수 있음)
    @CreationTimestamp // 시간이 자동으로 입력이 됨.
    private Timestamp createDate;
}
