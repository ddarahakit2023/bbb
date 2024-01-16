package com.woof.api.member.config;

import com.woof.api.member.jwt.filter.JwtFilter;
import com.woof.api.member.jwt.utils.TokenProvider;
import com.woof.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/member/*").permitAll()
                    .antMatchers("/member/authenticate").hasRole("USER")
                    .anyRequest().authenticated();

            http.addFilterBefore(new JwtFilter(memberService, tokenProvider), UsernamePasswordAuthenticationFilter.class);

            http.formLogin()
                    .loginProcessingUrl("/member/login")
                    .defaultSuccessUrl("/member/mypage");

            return http.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
