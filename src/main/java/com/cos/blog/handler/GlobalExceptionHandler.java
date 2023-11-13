package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어디서든 에러 받기 가능
@RestController
public class GlobalExceptionHandler {

    
    //IllegalArgumentException에 대한 모든 클래스를 받음
    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
