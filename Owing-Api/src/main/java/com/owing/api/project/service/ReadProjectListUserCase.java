package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.dto.response.ProjectListResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadProjectListUserCase {
    private final MemberUtils memberUtils;
    private final ProjectAdaptor projectAdaptor;
    private final ProjectMapper projectMapper;

    public ProjectListResponse execute() {
        Long memberId = memberUtils.getCurrentMemberId();
        List<Project> projectList = projectAdaptor.findAllByMemberId(memberId);
        return projectMapper.toListResponse(projectList);
    }
}
