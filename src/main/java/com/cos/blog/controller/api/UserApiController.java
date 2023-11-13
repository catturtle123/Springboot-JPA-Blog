package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class UserApiController {
    
        // DI가능 (Service가 Bean에 등록되어있기 때문)
        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/auth/joinProc")
        public ResponseDto<Integer> save(@RequestBody User user) {
            System.out.println(user.getEmail());
            System.out.println("UserApiController: save 호출됨");
            user.setRole(RoleType.USER);
            userService.회원가입(user);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }

        @PutMapping("/user")
        public ResponseDto<Integer> update(@RequestBody User user) {
            userService.회원수정(user);
            // 트랜잭션 종료로 DB값은 변경, but 세션 값은 변경되지 않음.
            // 즉 우리가 직접 세션 값을 변경해줄 것임. (세션에 직접 값을 넣는 것은 막혀있음)
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }
}
