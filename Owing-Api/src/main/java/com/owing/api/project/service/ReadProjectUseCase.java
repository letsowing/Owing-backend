package com.owing.api.project.service;

import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadProjectUseCase {

    private final ProjectAdapter projectAdapter;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectInfoResponse execute(Long projectId) {
        Project project = projectAdapter.findById(projectId);
        project.updateAccessed();
        return projectMapper.toInfoResponse(projectAdapter.save(project));
    }
}
