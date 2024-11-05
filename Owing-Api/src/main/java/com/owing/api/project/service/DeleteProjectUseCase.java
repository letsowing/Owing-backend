package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.service.ProjectNodeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectAdaptor projectAdaptor;

    private final ProjectNodeMapper projectMapper;
    private final ProjectNodeDomainService projectNodeDomainService;

    @Transactional
    public void execute(Long projectId) {
        Long memberId = memberUtils.getCurrentMemberId();
        Project project = projectAdaptor.findById(projectId);

        if (!project.getMember().getId().equals(memberId)) {
            throw ProjectIllegalAccessException.of(ProjectErrorCode.ILLEGAL_ACCESS,
                    "유효하지 않은 유저의 접근입니다. Member:%d --> Project:%d".formatted(memberId, projectId));
        }

        projectDomainService.deleteProject(project);

        ProjectNode projectNode = projectMapper.toNode(project.getId());
        projectNodeDomainService.deleteProjectNode(projectNode);
    }
}
