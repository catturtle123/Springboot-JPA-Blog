package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;
import com.cos.blog.test.Member;

// 사용자가 요청 -> 응답(Html파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
// @RestController
@RestController
public class HttpControllerTest {

    // 인터넷 브라우저 요청은 get 요청 밖에 할 수 없다.
    @GetMapping("/http/get")
    public String getTest(Member m){
        return "get 요청 : " + m.getId() + "," + m.getUsername();
    }

    @PostMapping("/http/post")
    public String postTest(@RequestBody String text){
        return "post 요청 : " + text;
    }

    @PutMapping("/http/put")
    public String putTest(){
        return "put 요청";
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }
}
