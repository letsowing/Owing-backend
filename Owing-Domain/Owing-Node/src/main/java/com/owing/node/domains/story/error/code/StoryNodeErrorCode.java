package com.owing.node.domains.story.error.code;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StoryNodeErrorCode implements OwingErrorCode {

    STORY_NODE_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "프로젝트 노드를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    StoryNodeErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "STORY_NODE" + code;
        this.message = message;
    }
}