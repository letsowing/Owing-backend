package com.owing.api.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.service.ProjectNodeDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectMapper projectMapper;
    private final ProjectNodeMapper projectNodeMapper;
    private final ProjectDomainService projectDomainService;
    private final ProjectNodeDomainService projectNodeDomainService;

    @Transactional("jpaTransactionManager")
    public ProjectShortInfoResponse execute(AddProjectRequest addProjectRequest) {
        Member memberReference = memberUtils.getCurrentMemberReference();

        Project project = projectMapper.toEntity(addProjectRequest, memberReference);
        Project savedProject = projectDomainService.createProjectEntity(project);

        ProjectNode projectNode = projectNodeMapper.toNode(savedProject.getId());
        projectNodeDomainService.createProjectNode(projectNode);

        return projectMapper.toShortInfoResponse(savedProject);
    }

}
