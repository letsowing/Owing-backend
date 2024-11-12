package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.*;
import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
@Tag(name="캐릭터 /cast", description="캐릭터 API")
public class CastController extends BaseFileController {

    private final CreateCastUseCase createCastUseCase;
    private final ReadCastUseCase readCastUseCase;
    private final UpdateCastUseCase updateCastUseCase;
    private final DeleteCastUseCase deleteCastUseCase;
    private final CreateConnectionUseCase createConnectionUseCase;
    private final UpdateConnectionUseCase updateConnectionUseCase;

    @GetMapping("/graph")
    @Operation(summary = "✨ 관계도: 캐릭터 ", description = "인물관계도 조회")
    public ResponseEntity<CastGraphResponse> getGraph(@RequestParam Long projectId) {
        return ResponseEntity.ok(readCastUseCase.executeGraph(projectId));
    }

    @PutMapping("/{castId}")
    @Operation(summary = "✨ 일반: 캐릭터 정보 수정", description = "캐릭터 정보 수정")
    public ResponseEntity<Void> updateCastInfo(@PathVariable Long castId, @RequestBody UpdateCastInfoRequest updateCastInfoRequest) {
        updateCastUseCase.executeUpdateInfo(castId, updateCastInfoRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{castId}")
    @Operation(summary = "✨ 관계도: 캐릭터 ", description = "인물관계도 내 위치 이동")
    public ResponseEntity<Void> updateCastCoordinate(@PathVariable Long castId, @RequestBody UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        updateCastUseCase.executeUpdateCoordinate(castId, updateCastCoordinateRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "✨ 일반: 캐릭터 생성", description = "캐릭터 생성")
    public ResponseEntity<CastInfoResponse> createCast(@RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(createCastUseCase.executeFull(createCastRequest));
    }

    @PostMapping("/relationships")
    @Operation(summary = "✨ 관계도: 인물 관계 생성", description = "인물 관계 생성")
    public ResponseEntity<CastRelationshipInfoResponse> createRelationship(@RequestBody CreateConnectionRequest createConnectionRequest) {
        return ResponseEntity.ok(createConnectionUseCase.execute(createConnectionRequest));
    }

    @PatchMapping("/relationships/{relationshipId}")
    public ResponseEntity<Void> updateRelationship(@PathVariable Long relationshipId, @RequestBody UpdateCastRelationshipLabelRequest updateCastRelationshipLabelRequest) {
        updateConnectionUseCase.executeLabel(relationshipId, updateCastRelationshipLabelRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/relationships/{relationshipId}/label")
    public ResponseEntity<Void> updateRelationshipLabel(@PathVariable Long relationshipId, @RequestBody UpdateCastRelationshipLabelRequest updateCastRelationshipLabelRequest) {
        updateConnectionUseCase.executeLabel(relationshipId, updateCastRelationshipLabelRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/relationships/{relationshipId}/handle")
    public ResponseEntity<Void> updateRelationshipHandle(@PathVariable Long relationshipId, @RequestBody UpdateCastRelationshipHandleRequest updateCastRelationshipHandleRequest) {
        updateConnectionUseCase.executeHandle(relationshipId, updateCastRelationshipHandleRequest);
        return ResponseEntity.noContent().build();
    }

    // Bean Setting
    @Override
    protected CreateDndUseCase<AddFileRequest> createDndUseCase() {
        return this.createCastUseCase;
    }

    @Override
    protected ReadDndUseCase readDndUseCase() {
        return this.readCastUseCase;
    }

    @Override
    protected DeleteDndUseCase deleteDndUseCase() {
        return this.deleteCastUseCase;
    }

    @Override
    protected UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> updateDndUseCase() {
        return this.updateCastUseCase;
    }
}
