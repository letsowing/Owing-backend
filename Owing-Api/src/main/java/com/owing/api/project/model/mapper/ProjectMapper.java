package com.owing.api.project.model.mapper;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoListResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.entity.common.model.dto.ProjectShortInfoDto;
import com.owing.entity.common.model.dto.ProjectInfoDto;
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

    public ProjectInfoDto toProjectInfo(UpdateProjectRequest updateProjectRequest) {
        return ProjectInfoDto.builder()
                .title(updateProjectRequest.title())
                .description(updateProjectRequest.description())
                .category(updateProjectRequest.category())
                .genres(updateProjectRequest.genres())
                .coverUrl(updateProjectRequest.coverUrl())
                .build();
    }

    public ProjectInfoResponse toInfoResponse(Project project) {
        Long projectId = project.getId();
        ProjectInfoDto projectInfoDto = ProjectInfoDto.builder()
                .title(project.getTitle())
                .description(project.getDescription())
                .category(project.getCategory())
                .genres(project.getGenres())
                .coverUrl(project.getCoverUrl())
                .build();
        return new ProjectInfoResponse(projectId, projectInfoDto);
    }

    public ProjectShortInfoResponse toShortInfoResponse(Project project) {
        ProjectShortInfoDto projectShortInfoDto = ProjectShortInfoDto.from(project);
        return new ProjectShortInfoResponse(projectShortInfoDto);
    }

    public ProjectShortInfoListResponse toListResponse(List<Project> projectList) {
        List<ProjectShortInfoDto> projectShortInfoDtoList = projectList.stream().map(ProjectShortInfoDto::from).toList();
        return new ProjectShortInfoListResponse(projectShortInfoDtoList);
    }
}
