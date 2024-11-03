package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectDetailResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.common.model.dto.ProjectInfoDto;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;
    private final ProjectAdaptor projectAdaptor;

    @Transactional
    public ProjectDetailResponse execute(Long projectId, UpdateProjectRequest updateProjectRequest) {
        Long memberId = memberUtils.getCurrentMemberId();
        Project oldProject = projectAdaptor.findById(projectId);

        if (!oldProject.getMember().getId().equals(memberId)) {
            throw ProjectIllegalAccessException.of(ProjectErrorCode.ILLEGAL_ACCESS,
                    "유효하지 않은 유저의 접근입니다. Member:%d --> Project:%d".formatted(memberId, projectId));
        }

        ProjectInfoDto updateInfo = projectMapper.toProjectInfo(updateProjectRequest);
        Project updateProject = projectDomainService.updateProject(oldProject, updateInfo);
        return projectMapper.toDetailResponse(updateProject);
    }

}
