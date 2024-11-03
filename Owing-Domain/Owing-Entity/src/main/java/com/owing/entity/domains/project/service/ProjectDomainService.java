package com.owing.entity.domains.project.service;

import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
