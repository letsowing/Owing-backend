package com.owing.api.project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.owing.entity.common.model.dto.ProjectShortInfoDto;

public record ProjectShortInfoResponse(
        @JsonUnwrapped
        ProjectShortInfoDto project
) {
}
