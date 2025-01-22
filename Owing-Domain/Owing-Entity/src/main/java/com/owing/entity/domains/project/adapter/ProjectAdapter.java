package com.owing.entity.domains.project.adapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectNotFoundException;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> ProjectNotFoundException.of(ProjectErrorCode.PROJECT_NOT_FOUND, "요청된 Project ID: %d".formatted(projectId)));
    }

    public List<Project> findAllByMemberId(Long memberId) {
        return projectRepository.findAllByMember_Id(memberId);
    }

    public Page<Project> getSortedProjectPage(Long memberId, Pageable pageable) {
        return projectRepository.findAllByMember_Id(memberId, pageable);
    }

    public List<Project> findRecentlyAccessedProjectList(Long memberId) {
        return projectRepository.findTop10ByMember_IdOrderByAccessedAtDesc(memberId);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }
}
