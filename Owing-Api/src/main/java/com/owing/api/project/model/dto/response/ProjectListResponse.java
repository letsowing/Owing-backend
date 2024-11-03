package com.owing.api.project.model.dto.response;

import com.owing.entity.common.vo.ProjectBasicVo;

import java.util.List;

public record ProjectListResponse(
        List<ProjectBasicVo> projectList
) {
}
