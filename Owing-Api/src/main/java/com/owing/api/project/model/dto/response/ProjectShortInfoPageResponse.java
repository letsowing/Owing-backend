package com.owing.api.project.model.dto.response;

import com.owing.entity.common.model.dto.ProjectShortInfoDto;
import org.springframework.data.domain.Page;

public record ProjectShortInfoPageResponse(
        Page<ProjectShortInfoDto> projectList
) {
}
