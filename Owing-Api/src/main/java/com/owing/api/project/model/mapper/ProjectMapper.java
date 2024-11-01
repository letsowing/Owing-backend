package com.owing.api.project.model.mapper;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectResponse;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectAdaptor projectAdaptor;

    public Project toEntity(AddProjectRequest addProjectRequest) {
        return Project.builder()
                .title(addProjectRequest.title())
                .description(addProjectRequest.description())
                .category(addProjectRequest.category())
                .genres(addProjectRequest.genres())
                .coverUrl(addProjectRequest.imageUrl())
                .build();
    }

    public ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getCoverUrl()
        );
    }

    public ProjectResponse toResponse(Long projectId) {
        Project project = projectAdaptor.find(projectId);
        return toResponse(project);
    }
}
