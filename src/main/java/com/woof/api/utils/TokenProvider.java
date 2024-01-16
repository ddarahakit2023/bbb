package com.woof.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Getter
public class TokenProvider {
    private final String secretKey;
    private final Integer expiredTimeMs;

    public TokenProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.token.expired-time-ms}") Integer expiredTimeMs) {
        this.secretKey = secretKey;
        this.expiredTimeMs = expiredTimeMs;
    }

    // 토큰 생성
    public static String generateAccessToken(String username, String key, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {

        return Keys.hmacShaKeyFor(secretKey.getBytes());

    }


    // 토큰에 담겨있는 정보를 이용해서 Claims 객체를 리턴하는 메소드
    public static Claims getClaims(String token, String key) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    // Claims 객체에서 username을 뽑아서 리턴하는 메소드
    public static String getUsername(String token, String key) {
        String username = getClaims(token, key).get("username", String.class);

        return username;
    }

    // 유효한 토큰인지 검증
    public static Boolean validate(String token, String username, String key) {
        String usernameByToken = getUsername(token, key);

        Date expireTime = getClaims(token, key).getExpiration();
        // 현재 시간 이전이 만료 시간이다. -> true
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        // 토큰에서 얻은 유저이름과 매개 변수로 받아온 유저이름이 같고
        // 현재 시간이 이후가 만료 시간이라면 토큰은 유효하다
        Boolean validation = usernameByToken.equals(username) && !result;

        return validation;
    }
}
