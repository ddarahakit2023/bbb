package com.woof.api.config;

import com.woof.api.config.filter.JwtFilter;
import com.woof.api.member.service.CeoService;
import com.woof.api.utils.TokenProvider;
import com.woof.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                    .antMatchers("/v2/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                    .antMatchers("/member/*").permitAll()
                    .antMatchers("/ceo/*").permitAll()
                    .antMatchers("/test/ceo").hasRole("CEO")
                    .antMatchers("/test/member").hasRole("MEMBER")
                    .anyRequest().authenticated();

            http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

            http.formLogin().disable();

            return http.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

