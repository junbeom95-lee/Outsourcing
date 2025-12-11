package com.example.outsourcing.common.util;

import com.example.outsourcing.common.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;


class JwtUtilTest {

    private JwtUtil jwtUtil;

    private static final String SECURITY_KEY = "testcodeveryhardpleasehelpmetestcodeveryhardpleasehelpme";

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(jwtUtil, "secretKeyString", SECURITY_KEY);
        jwtUtil.init();
    }

    @Test
    @DisplayName("JWT 토큰 생성 시  id, username, role 정보가 정상적으로 포함 되어있는지 테스트")
    void generateToken_ok() {

        // given
        Long id = 1L;
        String username = "jung-hyuk";
        UserRole role = UserRole.ADMIN;

        // when
        String token = jwtUtil.generateToken(id, username, role);
        JwtParser parser = (JwtParser) ReflectionTestUtils.getField(jwtUtil, "parser");
        assert parser != null;
        Claims claims = parser.parseSignedClaims(token).getPayload();

        // then
        assertThat(claims.get("username", String.class)).isEqualTo(username);
        assertThat(claims.get("role", String.class)).isEqualTo(role.name());
        assertThat(claims.getSubject()).isEqualTo(String.valueOf(id));

    }

    @Test
    @DisplayName("유효한 토큰인지 아닌지 검사 유효하면 true - 성공 케이스 ")
    void validateToken_ture() {

        // given
        Long id = 1L;
        String username = "jung-hyuk";
        UserRole role = UserRole.ADMIN;

        // when
        String token = jwtUtil.generateToken(id, username, role);

        boolean result = jwtUtil.validateToken(token);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유효한 토큰인지 아닌지 검사 유효하지 않으면 false - 실패 케이스 ")
    void validateToken_false() {

        // given
        String token = "beHappyDontWorry";

        // when
        boolean result = jwtUtil.validateToken(token);

        // then
        assertThat(result).isFalse();
    }

}