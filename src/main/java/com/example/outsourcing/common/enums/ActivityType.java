package com.example.outsourcing.common.enums;

public enum ActivityType {
    TASK_CREATED("작업 생성", "새로운 작업 \" title \"을 생성했습니다."),
    TASK_UPDATED("작업 수정", "새로운 작업 \" title \"을 생성했습니다."),
    TASK_DELETED("작업 삭제", "새로운 작업 \" title \"을 생성했습니다."),
    TASK_STATUS_CHANGED("작업 상태 변경", "작업 상태를 beforeStatus에서 AfterStatus로 변경했습니다."),
    COMMENT_CREATED("댓글 작성", "작업 \"  title \"에 댓글을 작성했습니다."),
    COMMENT_UPDATED("댓글 수정", "댓글을 수정했습니다."),
    COMMENT_DELETED("댓글 삭제", "댓글을 삭제했습니다."),
    LOGIN("로그인", "로그인 했습니다."),
    LOGOUT("로그아웃", "로그아웃 했습니다.")
    ;

    private final String action;
    private final String message;

    ActivityType(String action, String message) {
        this.action = action;
        this.message = message;
    }
}
