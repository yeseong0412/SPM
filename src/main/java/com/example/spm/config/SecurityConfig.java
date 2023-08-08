package com.example.spm.config;

import com.example.spm.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    // 참조 안에antMatchers()( 뿐만 아니라 mvcMathcers() 그리고 regexMatchers())는
    // Spring Security 6.0에서 더 이상 사용되지 않고 제거되었습니다. 따라서 Spring Boot 3 프로젝트에서는 사용할 수 없습니다.
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/members/**" , "/item/**", "/images/**").permitAll() //모든 사용자 인증
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 기본 정적 리소스 위치에 대한 접근 허용
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN") //어드민 페이지는 어드민만 인증되도룍
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/member/login")
                            .defaultSuccessUrl("/")
                            .usernameParameter("email")
                            .failureUrl("/members/login/error");
                })
                .logout(logout -> {
                    logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                            .logoutSuccessUrl("/");
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                ;

        return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return memberService;
    }
}
