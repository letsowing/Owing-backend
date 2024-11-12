package com.owing.api.project.controller;

import static com.owing.api.common.constant.OwingApiConst.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.common.constant.ProjectSort;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.service.CreateProjectPresignedUrlUseCase;
import com.owing.api.project.service.CreateProjectUseCase;
import com.owing.api.project.service.DeleteProjectUseCase;
import com.owing.api.project.service.ReadProjectListUseCase;
import com.owing.api.project.service.ReadProjectUseCase;
import com.owing.api.project.service.UpdateProjectUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
@Tag(name="프로젝트 /projects", description="프로젝트 API")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ReadProjectListUseCase readProjectListUseCase;
    private final ReadProjectUseCase readProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;
    private final CreateProjectPresignedUrlUseCase createProjectPresignedUrlUseCase;

    @PostMapping
    @Operation(summary = "✨ 프로젝트 생성", description = "프로젝트를 생성합니다.")
    public ResponseEntity<ProjectShortInfoResponse> createProject(@RequestBody AddProjectRequest addProjectRequest) {
        return ResponseEntity.ok(createProjectUseCase.execute(addProjectRequest));
    }

    @GetMapping
    @Operation(summary = "✨ 프로젝트 리스트 조회", description = "프로젝트 리스트를 조회합니다.")
    public ResponseEntity<ProjectShortInfoPageResponse> getProjectPage(
            @RequestParam(defaultValue = PAGE_DEFAULT) @Min(PAGE_MIN) int page,
            @RequestParam(defaultValue = PAGE_SIZE_DEFAULT) @Min(PAGE_SIZE_MIN) @Max(PAGE_SIZE_MAX) int size,
            @RequestParam ProjectSort projectSort
    ) {
        return ResponseEntity.ok(readProjectListUseCase.execute(page, size, projectSort));
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "✨ 프로젝트 조회", description = "프로젝트를 조회합니다.")
    public ResponseEntity<ProjectInfoResponse> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(readProjectUseCase.execute(projectId));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "✨ 프로젝트 수정", description = "프로젝트를 수정합니다.")
    public ResponseEntity<Void> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest
    ) {
        updateProjectUseCase.execute(projectId, updateProjectRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "✨ 프로젝트 삭제", description = "프로젝트를 삭제합니다.")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.execute(projectId);
        return ResponseEntity.ok().build();
    }

    /* presigned url 생성 */
    @GetMapping("/files/{fileExtension}")
    @Operation(summary = "✨ 일반: 작품 presignedUrl", description = "presigned url 생성합니다.")
    public ResponseEntity<?> getFile(@PathVariable(value = "fileExtension") String fileExtension) {
        return ResponseEntity.ok(createProjectPresignedUrlUseCase.execute(fileExtension));
    }
}
