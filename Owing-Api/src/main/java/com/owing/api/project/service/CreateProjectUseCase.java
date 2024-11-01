package com.owing.api.project.service;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProjectUseCase {

    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponse execute(AddProjectRequest addProjectRequest) {
        Project project = projectMapper.toEntity(addProjectRequest);
        Long savedProjectId = projectDomainService.createProject(project);
        return projectMapper.toResponse(savedProjectId);
    }

}
