package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.util.UUID;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${kakao.auth.client}")
    private String client;

    @Value("${kakao.auth.redirect}")
    private String redirect;

    @Value("${cos.password}")
    private String password;


    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("client", client);
        model.addAttribute("redirect",redirect);
        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallBack(String code) { // Data를 리턴해주는 컨트롤러 함수

        // POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // HttpsURLConnection
        // Retrofit2
        // OKHTTP
        // REST Template
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        
        // HttpHeader 오브젝트 생성
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id", client);
        params.add("redirect_uri",redirect);
        params.add("code",code);
        
        // HttpHeader와 HttpBody를 하나의 오브젝트에 넣기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        
        // Http 요청
        // 요청 url
        // http type
        // content
        // return class
        ResponseEntity<String> response = restTemplate.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("카카오 엑세스 토큰: " + oAuthToken.getAccess_token());

        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        // HttpHeader 오브젝트 생성
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        // HttpHeader와 HttpBody를 하나의 오브젝트에 넣기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        // 오브젝트 카카오 아이디, 이메일 출력 // 카카오는 패스워드를 가질 필요가 없음. -> 카카오를 받으면 바로 넣음
        // User 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호): " + kakaoProfile.getId());
        System.out.println("카카오 이메일: " + kakaoProfile.getKakao_account().email);

        System.out.println("블로그 서버 유저네임: " + kakaoProfile.getKakao_account().email+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().email);
//        UUID garbagePassword = UUID.randomUUID(); // 계속 바뀌기 때문에 안됨.
        System.out.println("블로그서버 패스워드: " + password);
        System.out.println("블로그 권한: " + "user");

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().email+"_"+kakaoProfile.getId())
                .password(String.valueOf(password))
                .email(kakaoProfile.getKakao_account().email)
                .oauth("kakao")
                .build();

        User originUser = userService.회원찾기(kakaoUser.getUsername());

        // 가입자 혹은 비가입자 체크해서 로그인 처리
        if (originUser.getUsername() == null) {
            System.out.println("기존 회원이 아닙니다.");
            userService.회원가입(kakaoUser);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } else {
            System.out.println("기존 회원이기에 자동 로그인을 진행합니다.");
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(originUser.getUsername(),password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }

        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
