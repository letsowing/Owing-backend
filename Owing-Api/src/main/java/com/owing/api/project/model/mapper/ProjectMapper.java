package com.owing.api.project.model.mapper;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectListResponse;
import com.owing.api.project.model.dto.response.ProjectResponse;
import com.owing.entity.common.model.dto.ProjectBasicDto;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectAdaptor projectAdaptor;

    public Project toEntity(AddProjectRequest addProjectRequest, Member member) {
        return Project.builder()
                .title(addProjectRequest.title())
                .description(addProjectRequest.description())
                .category(addProjectRequest.category())
                .genres(addProjectRequest.genres())
                .coverUrl(addProjectRequest.coverUrl())
                .member(member)
                .build();
    }

    public ProjectResponse toResponse(Project project) {
        ProjectBasicDto projectBasicDto = ProjectBasicDto.from(project);
        return new ProjectResponse(projectBasicDto);
    }

    public ProjectListResponse toListResponse(List<Project> projectList) {
        List<ProjectBasicDto> projectBasicDtoList = projectList.stream().map(ProjectBasicDto::from).toList();
        return new ProjectListResponse(projectBasicDtoList);
    }
}
