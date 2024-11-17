package com.owing.api.project.model.dto.response;

import com.owing.entity.common.model.dto.ProjectShortInfoDto;

import java.util.List;

public record ProjectShortInfoListResponse(
        List<ProjectShortInfoDto> projectList
) {
}
