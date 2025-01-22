package com.owing.api.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.constant.ProjectSort;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadProjectListUseCase {
    private final MemberUtils memberUtils;
    private final ProjectAdapter projectAdapter;
    private final ProjectMapper projectMapper;

    public ProjectShortInfoPageResponse execute(int page, int size, ProjectSort projectSort) {
        Long memberId = memberUtils.getCurrentMemberId();
        Sort sort = projectSort.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Project> projectPage = projectAdapter.getSortedProjectPage(memberId, pageable);
        return projectMapper.toPageResponse(projectPage);
    }
}
