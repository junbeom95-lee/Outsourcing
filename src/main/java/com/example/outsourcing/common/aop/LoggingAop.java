package com.example.outsourcing.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAop {
//          execution(* com.example.outsourcing.domain.comment.service..*.createComment.*(..)) ||
//          execution(* com.example.outsourcing.domain.comment.service..*.deleteComment.*(..)) ||
//          execution(* com.example.outsourcing.domain.comment.service..*.updateComment.*(..)) ||

    @Around("""
            execution(* com.example.outsourcing.domain.task.service..*.createTask(..)) ||
            execution(* com.example.outsourcing.domain.task.service..*.updateTask(..)) ||
            execution(* com.example.outsourcing.domain.task.service..*.deleteTask(..)) ||
            execution(* com.example.outsourcing.domain.task.service..*.changeTaskStatus(..)) ||
            execution(* com.example.outsourcing.domain.auth.service..*.login(..)) ||
            execution(* com.example.outsourcing.domain.user.service..*.delete(..))
            """)
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = requestAttributes.getRequest();

        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;

        String requestBody;
        byte[] content = requestWrapper.getContentAsByteArray();
        requestBody = (content.length > 0) ? new String(content, StandardCharsets.UTF_8) : "값이 없습니다.";

        Object usernameByJwt = request.getAttribute("username");
        String username = (String) usernameByJwt;

        LocalDateTime requestTime = LocalDateTime.now();

        String methodName = joinPoint.getSignature().getName();

        log.info("username: {} methodName: {} RequestTime: {} RequestBody: {} ", username, methodName, requestTime, requestBody);

        Object result = joinPoint.proceed();

        return result;

    }
}
