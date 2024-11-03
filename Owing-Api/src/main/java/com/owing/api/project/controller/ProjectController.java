package com.owing.api.project.controller;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectListResponse;
import com.owing.api.project.model.dto.response.ProjectResponse;
import com.owing.api.project.service.CreateProjectUseCase;
import com.owing.api.project.service.ReadProjectListUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ReadProjectListUserCase readProjectListUserCase;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        ProjectResponse projectResponse = createProjectUseCase.execute(addProjectRequest);
        return ResponseEntity.ok(projectResponse);
    }

    @GetMapping
    public ResponseEntity<ProjectListResponse> getProjectList() {
        ProjectListResponse projectListResponse = readProjectListUserCase.execute();
        return ResponseEntity.ok(projectListResponse);
    }
}
