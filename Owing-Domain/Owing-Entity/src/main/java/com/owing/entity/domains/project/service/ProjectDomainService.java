package com.owing.entity.domains.project.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectDomainService {

    private final ProjectAdapter projectAdapter;

    @Transactional
    public Project createProject(Project project) {
        Project savedProject = projectAdapter.save(project);
        return savedProject;
    }

    @Transactional
    public void updateProjectInfo(Project oldProject, ProjectInfo projectInfo) {
        oldProject.getProjectInfo().updateProjectInfo(projectInfo);
        projectAdapter.save(oldProject);
    }

    public Page<Project> getSortedProjectPage(Long memberId, Pageable pageable) {
        return projectAdapter.findAllByMemberId(memberId, pageable);
    }

    @Transactional
    public void deleteProject(Project project) {
        projectAdapter.deleteProject(project);
    }

    @Transactional
    public void updateAccessedAt(Project project, LocalDateTime localDateTime) {
        project.updateAccessedAt(localDateTime);
        projectAdapter.save(project);
    }

}
