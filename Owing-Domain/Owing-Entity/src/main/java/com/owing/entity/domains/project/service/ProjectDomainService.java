package com.owing.entity.domains.project.service;

import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
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
    public Project updateProjectInfo(Project oldProject, ProjectInfo projectInfo) {
        oldProject.getProjectInfo().updateTitle(projectInfo.getTitle());
        oldProject.getProjectInfo().updateDescription(projectInfo.getDescription());
        oldProject.getProjectInfo().updateCategory(projectInfo.getCategory());
        oldProject.getProjectInfo().updateGenres(projectInfo.getGenres());
        oldProject.getProjectInfo().updateCoverUrl(projectInfo.getCoverUrl());
        return projectAdaptor.save(oldProject);
    }

    @Transactional
    public void deleteProject(Project project) {
        projectAdaptor.deleteProject(project);
    }

}
