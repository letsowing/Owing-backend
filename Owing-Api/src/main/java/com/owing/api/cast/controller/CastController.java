package com.owing.api.cast.controller;

import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import lombok.RequiredArgsConstructor;
import com.owing.api.cast.service.CreateCastUseCase;
import com.owing.api.cast.service.CreateConnectionUseCase;
import com.owing.api.cast.service.DeleteCastUseCase;
import com.owing.api.cast.service.ReadCastUseCase;
import com.owing.api.cast.service.UpdateCastUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
@Tag(name="캐릭터 /cast", description="캐릭터 API")
public class CastController extends BaseFileController {

    private final CreateCastUseCase createCastUseCase;
    private final CreateConnectionUseCase createConnectionUseCase;
    private final ReadCastUseCase readCastUseCase;
    private final UpdateCastUseCase updateCastUseCase;
    private final DeleteCastUseCase deleteCastUseCase;

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

    // Bean Setting
    @Override
    protected CreateDndUseCase<?, AddFileRequest> createDndUseCase() {
        return this.createCastUseCase;
    }

    @Override
    protected ReadDndUseCase<?, ?> readDndUseCase() {
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
