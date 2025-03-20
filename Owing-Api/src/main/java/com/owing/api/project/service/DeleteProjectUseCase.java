package com.owing.api.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Project;
import com.owing.node.domains.project.service.ProjectNodeDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectAdapter projectAdapter;

    private final ProjectNodeDomainService projectNodeDomainService;

    @Transactional("jpaTransactionManager")
    public void execute(Long projectId) {
        Long memberId = memberUtils.getCurrentMemberId();
        Project project = projectAdapter.findById(projectId);

        if (!project.getMember().getId().equals(memberId)) {
            throw ProjectIllegalAccessException.of(ProjectErrorCode.ILLEGAL_ACCESS,
                    "유효하지 않은 유저의 접근입니다. Member:%d --> Project:%d".formatted(memberId, projectId));
        }

        projectAdapter.delete(project);
        projectNodeDomainService.deleteProjectNode(project.getId());
    }
}
