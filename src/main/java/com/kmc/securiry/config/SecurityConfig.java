package com.kmc.securiry.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor

public class SecurityConfig {

	// 정적인 파일에 대한 요청들
	// 정적인 파일에 대한 요청들
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
//    		"/login"
            "/css/**"
    		,"/img/**"
    		,"/js/**"
    		,"/scss/**"
    		,"/vendor/**"
    		//"/static/**"
    		
            
    };
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }
    
    @Bean
    protected SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {    
    	
    	
    	http.authorizeHttpRequests((auth) -> auth
    			.requestMatchers("/login","/signup").permitAll()
    			.anyRequest().authenticated());
    	
        http.formLogin(login -> login
                
                .loginPage("/login")    // GET 요청 (login form을 보여줌)                
                .usernameParameter("email")	// login에 필요한 id 값을 email로 설정 (default는 username)
                .passwordParameter("password")	// login에 필요한 password 값을 password(default)로 설정
                .loginProcessingUrl("/auth")    // POST 요청 (login 창에 입력한 데이터를 처리)
                .defaultSuccessUrl("/index")
                );	// login에 성공하면 /로 redirect
    			
    	http.logout(logout -> logout
    			.logoutUrl("/logout")
    			.logoutSuccessUrl("/login")
    					
    			);
    	
    	http.csrf((cf) -> cf.disable());
        return http.build();
    }

}