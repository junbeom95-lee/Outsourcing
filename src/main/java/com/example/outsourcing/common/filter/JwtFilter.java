package com.example.outsourcing.common.filter;

import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.example.outsourcing.common.util.JwtUtil.BEARER_PREFIX;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/users") || requestURI.equals("/api/auth/login")){
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            sendErrorResponse(response, "헤더에 토큰이 없습니다.");
            return;
        }

        String token = authorizationHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
             sendErrorResponse(response, "토큰 인증이 필요합니다");
             return;
        }

        Long userId = jwtUtil.extractUserId(token);
        String username = jwtUtil.extractUsername(token);
        UserRole role = jwtUtil.extractUserRole(token);

        request.setAttribute("username", username);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        CommonResponse<?> result = new CommonResponse<>(false, message, null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
