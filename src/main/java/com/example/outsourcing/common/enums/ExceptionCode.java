package com.example.outsourcing.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EXISTS_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_MATCHES_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    ;

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
