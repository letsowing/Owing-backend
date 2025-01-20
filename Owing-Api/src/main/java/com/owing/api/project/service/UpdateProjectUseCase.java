package com.owing.api.project.service;

import com.owing.common.util.MemberUtils;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;
    private final ProjectAdapter projectAdapter;

    @Transactional("jpaTransactionManager")
    public void execute(Long projectId, UpdateProjectRequest updateProjectRequest) {
        Long memberId = memberUtils.getCurrentMemberId();
        Project oldProject = projectAdapter.findById(projectId);

        if (!oldProject.getMember().getId().equals(memberId)) {
            throw ProjectIllegalAccessException.of(ProjectErrorCode.ILLEGAL_ACCESS,
                    "유효하지 않은 유저의 접근입니다. Member:%d --> Project:%d".formatted(memberId, projectId));
        }

        ProjectInfo updateInfo = projectMapper.toProjectInfo(updateProjectRequest);
        projectDomainService.updateProjectInfo(oldProject, updateInfo);
    }

}
