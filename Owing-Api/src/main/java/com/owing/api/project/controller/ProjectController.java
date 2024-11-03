package com.owing.api.project.controller;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectDetailResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoListResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.service.CreateProjectUseCase;
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

    @PostMapping
    public ResponseEntity<ProjectShortInfoResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        ProjectShortInfoResponse projectShortInfoResponse = createProjectUseCase.execute(addProjectRequest);
        return ResponseEntity.ok(projectShortInfoResponse);
    }

    @GetMapping
    public ResponseEntity<ProjectShortInfoListResponse> getProjectList() {
        ProjectShortInfoListResponse projectShortInfoListResponse = readProjectListUserCase.execute();
        return ResponseEntity.ok(projectShortInfoListResponse);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest
    ) {
        ProjectDetailResponse projectDetailResponse = updateProjectUseCase.execute(projectId, updateProjectRequest);
        return ResponseEntity.ok(projectDetailResponse);
    }
}
