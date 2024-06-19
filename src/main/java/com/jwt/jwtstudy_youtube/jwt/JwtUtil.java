package com.jwt.jwtstudy_youtube.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) { //보안을 위해서
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //토튼 만들기
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발생시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))// 소명 시간까지
                .signWith(secretKey) // 시그니처~!
                .compact();
    }
    public String getUsername(String token) { // 토큰 검사와 유저네임 추출

        return Jwts.parser()
                .verifyWith(secretKey) //토큰 변조여부등 검사
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getRole(String token) { // 토큰 검사와 권한 추출

        return Jwts.parser()
                .verifyWith(secretKey) //토큰 변조여부등 검사
                .build()
                .parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) { // 토큰 검사와 유효성 검사
        return Jwts.parser()
                .verifyWith(secretKey) //토큰 변조여부등 검사
                .build()
                .parseSignedClaims(token)
                .getPayload().getExpiration()
                .before(new Date());
    }

}
