package com.owing.api.project.controller;

import com.owing.api.common.constant.ProjectSort;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.service.CreateProjectUseCase;
import com.owing.api.project.service.DeleteProjectUseCase;
import com.owing.api.project.service.ReadProjectListUserCase;
import com.owing.api.project.service.UpdateProjectUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.owing.api.common.constant.OwingApiConst.*;

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
        return ResponseEntity.ok(createProjectUseCase.execute(addProjectRequest));
    }

    @GetMapping
    public ResponseEntity<ProjectShortInfoPageResponse> getProjectPage(
            @RequestParam(defaultValue = pageDefault) @Min(pageMin) int page,
            @RequestParam(defaultValue = pageSizeDefault) @Min(pageSizeMin) @Max(pageSizeMax) int size,
            @RequestParam ProjectSort projectSort
    ) {
        return ResponseEntity.ok(readProjectListUserCase.execute(page, size, projectSort));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectInfoResponse> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest
    ) {
        return ResponseEntity.ok(updateProjectUseCase.execute(projectId, updateProjectRequest));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.execute(projectId);
        return ResponseEntity.ok().build();
    }
}
