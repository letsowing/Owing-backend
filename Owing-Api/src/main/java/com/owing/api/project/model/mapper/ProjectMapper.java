package com.owing.api.project.model.mapper;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.ProjectInfoRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoListResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.common.model.dto.ProjectShortInfoDto;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectAdaptor projectAdaptor;

    public Project toEntity(AddProjectRequest addProjectRequest, Member member) {
        ProjectInfo projectInfo = toProjectInfo(addProjectRequest);
        return Project.builder()
                .projectInfo(projectInfo)
                .member(member)
                .build();
    }

    public ProjectInfo toProjectInfo(ProjectInfoRequest projectInfoRequest) {
        return ProjectInfo.builder()
                .title(projectInfoRequest.title())
                .description(projectInfoRequest.description())
                .category(projectInfoRequest.category())
                .genres(projectInfoRequest.genres())
                .coverUrl(projectInfoRequest.coverUrl())
                .build();
    }
    public ProjectInfoResponse toInfoResponse(Project project) {
        Long projectId = project.getId();
        ProjectInfo projectInfo = project.getProjectInfo();
        return new ProjectInfoResponse(projectId, projectInfo);
    }

    public ProjectShortInfoResponse toShortInfoResponse(Project project) {
        ProjectShortInfoDto projectShortInfoDto = ProjectShortInfoDto.from(project);
        return new ProjectShortInfoResponse(projectShortInfoDto);
    }

    public ProjectShortInfoListResponse toListResponse(List<Project> projectList) {
        List<ProjectShortInfoDto> projectShortInfoDtoList =
                projectList.stream()
                        .map(ProjectShortInfoDto::from)
                        .toList();
        return new ProjectShortInfoListResponse(projectShortInfoDtoList);
    }

    public ProjectShortInfoPageResponse toPageResponse(Page<Project> projectPage) {
        Page<ProjectShortInfoDto> projectShortInfoDtoPage = projectPage.map(ProjectShortInfoDto::from);
        return new ProjectShortInfoPageResponse(projectShortInfoDtoPage);
    }
}
