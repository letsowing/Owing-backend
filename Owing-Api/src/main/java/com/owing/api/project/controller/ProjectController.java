package com.owing.api.project.controller;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectResponse;
import com.owing.api.project.service.CreateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        ProjectResponse projectResponse = createProjectUseCase.execute(addProjectRequest);
        return ResponseEntity.ok(projectResponse);
    }
}
