package com.example.outsourcing.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    //user
    EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EXISTS_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_MATCHES_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    //task
    BAD_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    NOT_FOUND_TASK(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    BAD_REQUEST_CREATE_TASK(HttpStatus.BAD_REQUEST, "제목과 담당자는 필수입니다."),
    NOT_FOUND_AUTHOR(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    NOT_MATCHES_STATUS(HttpStatus.BAD_REQUEST,"유효하지 않은 상태 값입니다."),
    NOT_MATCHES_PRIORITY(HttpStatus.BAD_REQUEST,"유효하지 않은 중요도 값입니다."),
    NOT_AUTHOR_TASK(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),


    // comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_FOUND_PARENT_COMMENT(HttpStatus.NOT_FOUND, "부모 댓글을 찾을 수 없습니다."),
    NOT_AUTHOR_UPDATE_COMMENT(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다."),
    NOT_AUTHOR_DELETE_COMMENT(HttpStatus.FORBIDDEN, "댓글을 삭제할 권한이 없습니다."),
    REQUIRED_COMMENT(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다."),

    // team
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),
    BAD_REQUEST_CREATE_TEAM(HttpStatus.NOT_FOUND , "팀 이름은 필수입니다."),
    EXISTS_TEAM_NAME(HttpStatus.NOT_FOUND , "이미 존재하는 팀 이름입니다."),
    FORBIDDEN_CREATE(HttpStatus.FORBIDDEN, "생성 권한이 없습니다."),
    FORBIDDEN_UPDATE(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    FORBIDDEN_DELETE(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),
    TEAM_HAS_MEMBER(HttpStatus.CONFLICT, "팀에 멤버가 존재하여 삭제할 수 없습니다."),
    ALREADY_TEAM_MEMBER(HttpStatus.CONFLICT, "이미 팀에 속한 멤버입니다."),
    TEAM_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 팀에 속한 멤버입니다."),
    FORBIDDEN_REMOVE(HttpStatus.NOT_FOUND, "이미 팀에 속한 멤버입니다."),


    ;

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
