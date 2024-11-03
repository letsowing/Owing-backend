package com.owing.api.project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.owing.entity.common.model.dto.ProjectBasicDto;

public record ProjectResponse(
        @JsonUnwrapped
        ProjectBasicDto project
) {
}
