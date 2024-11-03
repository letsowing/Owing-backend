package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.project.model.dto.response.ProjectShortInfoListResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadProjectListUserCase {
    private final MemberUtils memberUtils;
    private final ProjectAdaptor projectAdaptor;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    public ProjectShortInfoListResponse execute() {
        Long memberId = memberUtils.getCurrentMemberId();
        List<Project> projectList = projectAdaptor.findAllByMemberId(memberId);
        return projectMapper.toListResponse(projectList);
    }

    public ProjectShortInfoListResponse executeRecentlyAccessedList() {
        Long memberId = memberUtils.getCurrentMemberId();
        List<Project> projectList = projectDomainService.getRecentlyAccessedProjectList(memberId);
        return projectMapper.toListResponse(projectList);
    }

    public ProjectShortInfoPageResponse executeLatestPage(int page, int size) {
        Long memberId = memberUtils.getCurrentMemberId();
        Page<Project> projectPage = projectDomainService.getLatestProjectPage(memberId, page, size);
        return projectMapper.toPageResponse(projectPage);
    }
}
