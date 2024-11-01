package com.owing.entity.domains.project.service;

import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectDomainService {

    private final ProjectAdaptor projectAdaptor;

    public Long createProject(Project project) {
        Project savedProject = projectAdaptor.save(project);
        return savedProject.getId();
    }
}
