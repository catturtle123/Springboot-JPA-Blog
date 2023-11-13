package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean // IOC가 됨!
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encode());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf().disable() // csrf 토큰 비활성화 (테스트시 비활성화가 좋음)
                .authorizeRequests()
                .antMatchers("/auth/**","/","/js/**","/css/**","/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm") // 인증이 안되었을 시
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해줌.
                .defaultSuccessUrl("/"); // 정상적으로 요청이 완료가 되면 여기로 감.
    }
}