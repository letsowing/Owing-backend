package com.owing.api.cast.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.request.GenerateCastImageRequest;
import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.dto.request.UpdateCastRelationshipLabelRequest;
import com.owing.api.cast.model.dto.request.UpdateCastRelationshipRequest;
import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.service.dnd.CastCrudCrudService;
import com.owing.api.cast.service.CreateCastPresignedUrlUseCase;
import com.owing.api.cast.service.CreateConnectionUseCase;
import com.owing.api.cast.service.DeleteConnectionUseCase;
import com.owing.api.cast.service.GenerateCastImageUseCase;
import com.owing.api.cast.service.ReadCastUseCase;
import com.owing.api.cast.service.UpdateCastUseCase;
import com.owing.api.cast.service.UpdateConnectionUseCase;
import com.owing.api.dnd.controller.BaseFileController;
import com.owing.api.dnd.model.dto.request.AddFileRequest;
import com.owing.api.dnd.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.service.DndCrudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
@Tag(name="캐릭터 /cast", description="캐릭터 API")
public class CastController extends BaseFileController {

    private final CastCrudCrudService castCrudService;
    private final UpdateCastUseCase updateCastUseCase;
    private final ReadCastUseCase readCastUseCase;
    private final CreateConnectionUseCase createConnectionUseCase;
    private final UpdateConnectionUseCase updateConnectionUseCase;
    private final DeleteConnectionUseCase deleteConnectionUseCase;
    private final CreateCastPresignedUrlUseCase createCastPresignedUrlUseCase;
    private final GenerateCastImageUseCase generateCastImageUseCase;

    @GetMapping("/graph")
    @Operation(summary = "✨ 관계도: 캐릭터 ", description = "인물관계도 조회")
    public ResponseEntity<CastGraphResponse> getGraph(@RequestParam Long projectId) {
        return ResponseEntity.ok(readCastUseCase.executeGraph(projectId));
    }

    @PutMapping("/{castId}")
    @Operation(summary = "✨ 일반: 캐릭터 정보 수정", description = "캐릭터 정보 수정")
    public ResponseEntity<Void> updateCastInfo(@PathVariable Long castId, @Valid @RequestBody UpdateCastInfoRequest updateCastInfoRequest) {
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
    public ResponseEntity<CastInfoResponse> createCast(@Valid @RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(castCrudService.executeFull(createCastRequest));
    }

    @PostMapping("/relationships")
    @Operation(summary = "✨ 관계도: 인물 관계 생성", description = "인물 관계 생성")
    public ResponseEntity<CastRelationshipInfoResponse> createRelationship(@Valid @RequestBody CreateConnectionRequest createConnectionRequest) {
        return ResponseEntity.ok(createConnectionUseCase.execute(createConnectionRequest));
    }

    @PatchMapping("/relationships/{relationshipId}/label")
    @Operation(summary = "✨ 관계도: 인물 관계 수정", description = "인물 관계의 라벨을 수정")
    public ResponseEntity<Void> updateRelationshipLabel(@PathVariable Long relationshipId, @Valid @RequestBody UpdateCastRelationshipLabelRequest updateCastRelationshipLabelRequest) {
        updateConnectionUseCase.executeLabel(relationshipId, updateCastRelationshipLabelRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/relationships/{relationshipId}")
    @Operation(summary = "✨ 관계도: 인물 관계 수정", description = "source || target 노드, Handle 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "기존 관계와 source & target이 같다면 handle만 수정"),
            @ApiResponse(responseCode = "200", description = "기존 관계와 source & target이 다르다면 기존 관계 삭제 & 새로운 관계 추가. 응답은 생성과 같음")
    })
    public ResponseEntity<?> updateRelationship(@PathVariable Long relationshipId, @Valid @RequestBody UpdateCastRelationshipRequest updateCastRelationshipRequest) {
        Optional<CastRelationshipInfoResponse> optional = updateConnectionUseCase.execute(relationshipId, updateCastRelationshipRequest);
        if (optional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @DeleteMapping("/relationships/{relationshipId}")
    @Operation(summary = "✨ 관계도: 인물 관계 삭제", description = "인물 관계를 삭제")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long relationshipId) {
        deleteConnectionUseCase.execute(relationshipId);
        return ResponseEntity.noContent().build();
    }

    /* presigned url 생성 */
    @GetMapping("/files/{fileExtension}")
    @Operation(summary = "✨ 일반: 인물 presignedUrl", description = "presigned url 생성합니다.")
    public ResponseEntity<?> getFile(@PathVariable(value = "fileExtension") String fileExtension) {
        return ResponseEntity.ok(createCastPresignedUrlUseCase.execute(fileExtension));
    }

    /* OpenAI - 세계관 이미지 생성 요청 후 S3 업로드 */
    @PostMapping("/images")
    @Operation(summary = "✨ OpenAI: 인물 이미지 생성 요청 후 S3 업로드", description = "인물 이미지 생성 요청 후 S3 업로드")
    public ResponseEntity<CastImageResponse> generateCastImage(@RequestBody GenerateCastImageRequest generateCastImageRequest) {
        return ResponseEntity.ok(generateCastImageUseCase.execute(generateCastImageRequest));
    }

    @Override
    protected DndCrudService<AddFileRequest, UpdateFileTitleRequest, UpdateFilePositionRequest> dndCrudService() {
        return castCrudService;
    }
}
