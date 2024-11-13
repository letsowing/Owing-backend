package com.owing.ai.domains.story.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum StoryAIErrorCode implements OwingErrorCode {

    SPELL_CHECK_FAILED(HttpStatus.BAD_REQUEST, "0001", "맞춤법 검사 기능 오류"),
    CRASH_CHECK_FAILED(HttpStatus.BAD_REQUEST, "0002", "설정 충돌 검사 기능 오류");


    private final HttpStatus status;
    private final String code;
    private final String message;

    StoryAIErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "STORY_AI" + code;
        this.message = message;
    }
}