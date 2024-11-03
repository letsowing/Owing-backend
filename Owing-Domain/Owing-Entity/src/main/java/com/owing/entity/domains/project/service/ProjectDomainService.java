package com.owing.entity.domains.project.service;

import com.owing.entity.common.model.dto.ProjectInfoDto;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectDomainService {

    private final ProjectAdaptor projectAdaptor;

    @Transactional
    public Project createProject(Project project) {
        Project savedProject = projectAdaptor.save(project);
        return savedProject;
    }

    @Transactional
    public Project updateProject(Project oldProject, ProjectInfoDto projectInfoDto) {
        oldProject.updateTitle(projectInfoDto.title());
        oldProject.updateDescription(projectInfoDto.description());
        oldProject.updateCategory(projectInfoDto.category());
        oldProject.updateGenres(projectInfoDto.genres());
        oldProject.updateCoverUrl(projectInfoDto.coverUrl());
        return projectAdaptor.save(oldProject);
    }
}
