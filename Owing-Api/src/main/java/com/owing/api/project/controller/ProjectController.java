package com.owing.api.project.controller;

import com.owing.api.common.constant.ProjectSort;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.service.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.owing.api.common.constant.OwingApiConst.*;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ReadProjectListUseCase readProjectListUseCase;
    private final ReadProjectUseCase readProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    @PostMapping
    public ResponseEntity<ProjectShortInfoResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        return ResponseEntity.ok(createProjectUseCase.execute(addProjectRequest));
    }

    @GetMapping
    public ResponseEntity<ProjectShortInfoPageResponse> getProjectPage(
            @RequestParam(defaultValue = PAGE_DEFAULT) @Min(PAGE_MIN) int page,
            @RequestParam(defaultValue = PAGE_SIZE_DEFAULT) @Min(PAGE_SIZE_MIN) @Max(PAGE_SIZE_MAX) int size,
            @RequestParam ProjectSort projectSort
    ) {
        return ResponseEntity.ok(readProjectListUseCase.execute(page, size, projectSort));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectInfoResponse> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(readProjectUseCase.execute(projectId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest
    ) {
        updateProjectUseCase.execute(projectId, updateProjectRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.execute(projectId);
        return ResponseEntity.ok().build();
    }
}
