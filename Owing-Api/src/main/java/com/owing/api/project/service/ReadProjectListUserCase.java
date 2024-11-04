package com.owing.api.project.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.common.constant.ProjectOrder;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadProjectListUserCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    public ProjectShortInfoPageResponse execute(int page, int size, ProjectOrder projectOrder) {
        Long memberId = memberUtils.getCurrentMemberId();
        Sort sort = projectOrder.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Project> projectPage = projectDomainService.getOrderedProjectPage(memberId, pageable);
        return projectMapper.toPageResponse(projectPage);
    }
}
