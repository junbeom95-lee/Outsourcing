package com.example.outsourcing.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EXISTS_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_MATCHES_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    BAD_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    NOT_FOUND_TASK(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    BAD_REQUEST_CREATE_TASK(HttpStatus.BAD_REQUEST, "제목과 담당자는 필수입니다."),
    NOT_FOUND_AUTHOR(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    NOT_MATCHES_STATUS(HttpStatus.BAD_REQUEST,"유효하지 않은 상태 값입니다."),
    NOT_MATCHES_PRIORITY(HttpStatus.BAD_REQUEST,"유효하지 않은 중요도 값입니다."),
    NOT_AUTHOR_TASK(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
