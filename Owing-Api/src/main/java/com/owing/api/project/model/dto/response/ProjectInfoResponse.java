package com.owing.api.project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.owing.entity.common.model.dto.ProjectInfoDto;

public record ProjectInfoResponse(
        Long projectId,
        @JsonUnwrapped
        ProjectInfoDto projectInfoDto
) {
}
