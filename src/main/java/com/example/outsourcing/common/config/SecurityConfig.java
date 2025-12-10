package com.example.outsourcing.common.config;

import com.example.outsourcing.common.filter.JwtFilter;
import com.example.outsourcing.domain.auth.service.UserinfoDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final UserinfoDetailService userinfoDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .userDetailsService(userinfoDetailService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users", "/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    // 교차 출처 리소스 공유 (CORS: Cross-Origin Resource Sharing)
    // 브라우저가 자신의 출처가 아닌 다른 어떤 출처로부터 자원을 요청하는 것에 대해 허용하도록 서버가 이를 허가해 주는 HTTP 헤더 기반 메커니즘
    // 전역 교차 출처 요청 처리 구성 메서드
    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();

            config.setAllowedOrigins(List.of("http://localhost:3000"));                        // 모든 오리진(출처)에서의 요청을 허용
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용되는 HTTP 메서드를 지정
            config.setAllowedHeaders(List.of("*"));                                            // 모든 헤더를 허용
            config.setExposedHeaders(List.of("Authorization"));                                // 브라우저에 노출할 응답 헤더를 지정
            config.setAllowCredentials(true);                                                      // 인증된 요청을 허용 (ex: HTTP 인증)

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);                                // 모든 경로에 대해 CORS 설정을 적용

            return source;
        }
    }

