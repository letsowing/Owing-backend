package com.owing.entity.folders.story.error;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StoryErrorCode implements OwingErrorCode {

    STORY_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "등장인물을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    StoryErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "MEMBER" + code;
        this.message = message;
    }
}