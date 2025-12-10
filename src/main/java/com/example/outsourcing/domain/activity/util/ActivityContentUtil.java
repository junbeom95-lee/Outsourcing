package com.example.outsourcing.domain.activity.util;

import org.springframework.stereotype.Component;

@Component
public class ActivityContentUtil {

    public String getLogTaskCreate(String title) {
        return "새로운 작업 \"" + title + "\"을 생성했습니다.";
    }

    public String getLogTaskStatusChange(String beforeStatus, String AfterStatus) {
        return "작업 상태를 " + beforeStatus + "에서 " + AfterStatus + "로 변경했습니다." ;
    }

    public String getLogCommentCreate(String title) {
        return "작업 \"" + title + "\"에 댓글을 작성했습니다.";
    }

    public String getLogTaskUpdate(String title) {
        return "작업 \"" + title + "\" 정보를 수정했습니다.";
    }

    public String getLogTaskDelete(String title) {
        return "작업 \"" + title + "\"을 삭제했습니다.";
    }

    public String getLogCommentUpdate() {
        return "댓글을 수정했습니다.";
    }

    public String getLogCommentDelete() {
        return "댓글을 삭제했습니다.";
    }

}
