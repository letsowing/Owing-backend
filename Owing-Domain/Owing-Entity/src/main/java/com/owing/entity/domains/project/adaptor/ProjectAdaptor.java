package com.owing.entity.domains.project.adaptor;

import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectException;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectAdaptor {
    private final ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> ProjectException.of(ProjectErrorCode.PROJECT_NOT_FOUND, "요청된 Project ID: %d".formatted(projectId)));
    }

    public List<Project> findAllByMemberId(Long memberId) {
        return projectRepository.findAllByMember_Id(memberId);
    }
}
