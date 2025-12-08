package com.example.outsourcing.common.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";   // 헤더 BearerToken
    private static final long TOKEN_TIME = 60 * 60 * 1000L; // 토큰 유효시간(60분)

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey key;
    private JwtParser parser;

    // 토큰 생성
    public String generateToken(String email) {
        Date now = new Date();
        return  Jwts.builder()
                .claim("email", email)
                .issuedAt(now)                                       // 발급 시간
                .expiration(new Date(now.getTime() + TOKEN_TIME))    // 토큰 유효시간
                .signWith(key, Jwts.SIG.HS256)                       // 서명 알고리즘과 비밀 키 = signature
                .compact();                                          // jwt 발급
    }
}
