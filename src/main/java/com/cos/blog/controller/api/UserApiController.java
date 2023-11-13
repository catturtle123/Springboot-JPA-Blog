package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserApiController {
    
        // DI가능 (Service가 Bean에 등록되어있기 때문)
        @Autowired
        private UserService userService;

        @PostMapping("/auth/joinProc")
        public ResponseDto<Integer> save(@RequestBody User user) {
            System.out.println("UserApiController: save 호출됨");
            user.setRole(RoleType.USER);
            userService.회원가입(user);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }

        @PutMapping("/user")
        public ResponseDto<Integer> update(@RequestBody User user) {
            userService.회원수정(user);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }
}
