package com.owing.api.project.model.dto.response;

import com.owing.entity.common.model.dto.ProjectBasicDto;

import java.util.List;

public record ProjectListResponse(
        List<ProjectBasicDto> projectList
) {
}
