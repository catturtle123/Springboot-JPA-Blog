package com.cos.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> 자바(다른언어) OBJECT -> 테이블로 매핑이 됨.

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // 빌더 패턴!!!
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // insert시에 null인 필드를 없애준다.
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략
    private int id;

    @Column(nullable = false,length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false,length = 100) // 123456 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;
    
    // enum을 쓰는 것이 좋음
    // 이것만 자동으로 안들어가는 데 --> 쿼리가 (role = null로 들어가기 때문 / 아무것도 없어야 default값이 들어가기 때문 dynamic 필요)
//    @ColumnDefault("'user'") // ''을 붙여주어야함.
    @Enumerated(EnumType.STRING) // DB에는 Enum이 없기 때문
    private RoleType role; // Enum을 쓰는 게 좋다.  // admin, user, manager => 도메인 설정 가능 (범위 설정 -> 오타가 나도 막을 수 있음)
    
    // 자바에서 자동으로 insert 해줌
    @CreationTimestamp // 시간이 자동으로 입력이 됨.
    private Timestamp createDate;
}
