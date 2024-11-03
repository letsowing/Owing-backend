package com.owing.api.project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.owing.entity.common.vo.ProjectBasicVo;

public record ProjectResponse(
        @JsonUnwrapped
        ProjectBasicVo project
) {
}
