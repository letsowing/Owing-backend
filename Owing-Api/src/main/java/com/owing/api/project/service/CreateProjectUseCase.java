package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.service.ProjectNodeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    private final ProjectNodeDomainService projectNodeDomainService;
    private final ProjectNodeMapper projectNodeMapper;

    @Transactional
    public ProjectShortInfoResponse execute(AddProjectRequest addProjectRequest) {
        Member memberReference = memberUtils.getCurrentMemberReference();

        Project savedProject = createEntity(addProjectRequest, memberReference);
        createNode(savedProject);

        return projectMapper.toShortInfoResponse(savedProject);
    }

    private Project createEntity(AddProjectRequest addProjectRequest, Member memberReference) {
        Project project = projectMapper.toEntity(addProjectRequest, memberReference);
        return projectDomainService.createProject(project);
    }

    private void createNode(Project savedProject) {
        ProjectNode projectNode = projectNodeMapper.toNode(savedProject.getId());
        projectNodeDomainService.createProjectNode(projectNode);
    }
}
