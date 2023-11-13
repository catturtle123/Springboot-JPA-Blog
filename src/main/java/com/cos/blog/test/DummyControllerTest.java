package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    // save함수는 id를 전달하지 않으면 insert를 해주고
    // id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // id를 전달하고 해당 id에 대한 데이터가 없으면 insert를 해준다.
    //email, password
    @Transactional // 이게 있으면 save를 쓰지 않아도 수정 가능 (더티체킹) // 함수가 종료시 바로 commit이 됨.
    @PutMapping("/dummy/user/{id}")             // JackSon이 바꾸어줌.
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {

        // 영속화됨.
        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        // 일부 속성이 바뀜.
        user.setEmail(requestUser.getEmail());
        user.setPassword(requestUser.getPassword());

        // userRepository.save(user);
        
        // 더티 체킹

        // 종료시 자동 commit -> 변경 인식 -> DB에 밀어넣어서 update문 실행 / 아무것도 안 바뀌었다면 x / 바뀌었다면(영속화 되어있을 때) 변경을 감지하여 DB에 수정을 날림
        return user;
    }
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }
    
    // 한페이지당 2건의 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // 페이지 관련 값을 가지고 싶다면 Page<User>로 리턴
        Page<User> pagingUser = userRepository.findAll(pageable);
        
        if(pagingUser.isFirst()) {
          // 페이지 클래스가 제공해줌
        }
        List<User> users = pagingUser.getContent();
        return users;
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음.
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌 지 판단하고 리턴해
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자가 없습니다.");
            }
        });

        // 람다식 사용식
//        User user2 = userRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("");
//        });
        
        // 요청 : 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 얘가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MesssageConverter가 Jackson을 이용함.
        // 브라우저에게 던져줌.
        return user;
    }

    // http://localhost:8000/blog/dummy/join (요청)
    @PostMapping("/dummy/join")
    public String join(User user) {
        user.setRole(RoleType.USER); // default보단 직접 넣기
        System.out.println(user.toString());
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {

        userRepository.findById(id);

        try { // 오류가 나기에 오류를 날려주어야함.
            userRepository.deleteById(id); // 리턴이 없음.
        } catch (Exception e) { // EmptyResultDataAccessException 발생 시
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        // 삭제되었기에
        return "삭제되었습니다. id: " + id;
    }
}
