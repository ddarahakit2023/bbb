package com.woof.member.jwt.filter;

import com.woof.jwt.utils.TokenProvider;
import com.woof.member.model.Member;
import com.woof.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ")[1];
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        String username = TokenProvider.getUsername(token, tokenProvider.getSecretKey());

        // 인증 과정 수행 회원 엔티티를 받아온다.
        // member.getUsername();
        // username = email이라고 정함
        Member member = memberService.getMemberByEmail(username);
        String memberUsername = member.getUsername();
        if (!TokenProvider.validate(token, memberUsername, tokenProvider.getSecretKey())) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                member, null,
                member.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인가하는 코드
        filterChain.doFilter(request, response);

    }
}
