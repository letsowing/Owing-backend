package com.owing.api.project.controller;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoListResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.service.CreateProjectUseCase;
import com.owing.api.project.service.DeleteProjectUseCase;
import com.owing.api.project.service.ReadProjectListUserCase;
import com.owing.api.project.service.UpdateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ReadProjectListUserCase readProjectListUserCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    @PostMapping
    public ResponseEntity<ProjectShortInfoResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        ProjectShortInfoResponse projectShortInfoResponse = createProjectUseCase.execute(addProjectRequest);
        return ResponseEntity.ok(projectShortInfoResponse);
    }

    @GetMapping("/accessed")
    public ResponseEntity<ProjectShortInfoListResponse> getProjectList() {
        ProjectShortInfoListResponse projectShortInfoListResponse = readProjectListUserCase.executeRecentlyAccessedList();
        return ResponseEntity.ok(projectShortInfoListResponse);
    }

    @GetMapping("/created")
    public ResponseEntity<ProjectShortInfoPageResponse> getProjectPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ProjectShortInfoPageResponse projectShortInfoPageResponse = readProjectListUserCase.executeLatestPage(page, size);
        return ResponseEntity.ok(projectShortInfoPageResponse);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectInfoResponse> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest
    ) {
        ProjectInfoResponse projectInfoResponse = updateProjectUseCase.execute(projectId, updateProjectRequest);
        return ResponseEntity.ok(projectInfoResponse);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.execute(projectId);
        return ResponseEntity.ok().build();
    }
}
