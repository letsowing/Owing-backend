package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectShortInfoResponse execute(AddProjectRequest addProjectRequest) {
        Member memberReference = memberUtils.getCurrentMemberReference();
        Project project = projectMapper.toEntity(addProjectRequest, memberReference);
        Project savedProject = projectDomainService.createProject(project);
        return projectMapper.toShortInfoResponse(savedProject);
    }

}
