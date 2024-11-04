package com.owing.api.project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.owing.entity.domains.project.model.ProjectInfo;

public record ProjectInfoResponse(
        Long projectId,
        @JsonUnwrapped
        ProjectInfo projectInfoDto
) {
}
