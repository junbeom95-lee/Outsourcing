package com.example.outsourcing.common.filter;

import com.example.outsourcing.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/auth/login") || requestURI.equals("/api/users")){filterChain.doFilter(request, response); return;}

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)){ response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"JWT토큰이 필요합니다.");return;}

        String token = authorizationHeader.substring(7);

        if (!jwtUtil.validateToken(token)){response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return;}

        Long userId = jwtUtil.extractUserId(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
}
