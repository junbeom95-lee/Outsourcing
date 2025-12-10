package com.example.outsourcing.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 교차 출처 리소스 공유 (CORS: Cross-Origin Resource Sharing)
    // 브라우저가 자신의 출처가 아닌 다른 어떤 출처로부터 자원을 요청하는 것에 대해 허용하도록 서버가 이를 허가해 주는 HTTP 헤더 기반 메커니즘
    // 전역 교차 출처 요청 처리 구성 메서드
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                                     // 모든 경로에 대해 CORS 설정을 적용
                .allowedOrigins("http://localhost:3000")                              // 모든 오리진(출처)에서의 요청을 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")   // 허용되는 HTTP 메서드를 지정
                .allowedHeaders("*")                                                  // 헤더를 허용
                .exposedHeaders("Authorization")                                      // 브라우저에 노출할 응답 헤더를 지정
                .allowCredentials(true)                                               // 인증된 요청을 허용 (ex: HTTP 인증)
                .maxAge(3600);
    }
}

