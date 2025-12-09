package com.example.outsourcing.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
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

    // @PostConstruct 어플리케이션 실행 될 때 가장 먼저 실행 되게 하는 어노테이션
    // JWT 서명 검증을 위한 Key 준비 + parser 준비 (빈 초기화 메서드)
    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(secretKeyString);      //  secret key → byte[]로 변환
        this.key = Keys.hmacShaKeyFor(bytes);                        // JWT 서명을 검증할 Key 객체 생성
        this.parser = Jwts.parser()                                  // 나중에 JWT 토큰을 파싱·검증할 parser 생성
                .verifyWith(this.key)
                .build();
    }

    // 토큰 생성
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        return Jwts.builder()
                        .subject(userId.toString())
                        .claim("username", username)
                        .issuedAt(now)                                       // 발급 시간
                        .expiration(new Date(now.getTime() + TOKEN_TIME))    // 토큰 유효시간
                        .signWith(key, Jwts.SIG.HS256)                       // 서명 알고리즘과 비밀 키 = signature
                        .compact();                                          // jwt 발급
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) return false;          // 토큰 없을 시 실패

        try {
            parser.parseSignedClaims(token);                         // 클레임 파싱
            return true;
        } catch (JwtException | IllegalArgumentException e) {        // 개별 예외 분리 없음: 서명/형식/만료 등 모든 실패를 한 번에 처리
            log.debug("Invalid JWT: {}", e.toString());
            return false;
        }
    }

    // 토큰 복호화
    private Claims extractAllClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

    public Long extractUserId(String token) { return Long.valueOf(extractAllClaims(token).getSubject()); }

    // public String extractUsername(String token) {return extractAllClaims(token).get("username",String.class);}



}
