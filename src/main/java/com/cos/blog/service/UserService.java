package com.cos.blog.service;

import com.cos.blog.config.SecurityConfig;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IOC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화를 시키고, 영속화된 User 오브젝트를 수정
        // 영속화
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{return new IllegalArgumentException("회원 찾기가 실패했습니다.");});
        String rawPassword = user.getPassword();
        String encodePassword = encoder.encode(rawPassword);
        persistance.setPassword(encodePassword);
        persistance.setEmail(user.getEmail());
        // 회원수정 함수 종료 시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됩니다.

    }

//    @Transactional(readOnly = true) // select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
//    public User 로그인(User user) {
//        User user1 = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//        return user1;
//    }
}
