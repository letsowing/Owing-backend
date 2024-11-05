package com.owing.node.folder.story.error.code;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StoryFolderNodeErrorCode implements OwingErrorCode {

    STORY_FOLDER_NODE_ERROR_CODE(HttpStatus.NOT_FOUND, "001", "원고 폴더 노드를 찾을 수 없습니다."),
    RELATIONSHIP_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "002", "소속된 프로젝트가 존재합니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    StoryFolderNodeErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "STORY_FOLDER_NODE" + code;
        this.message = message;
    }
}