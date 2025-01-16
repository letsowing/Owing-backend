package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.*;
import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.service.*;
import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        return ResponseEntity.ok(createCastUseCase.executeFull(createCastRequest));
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
    public ResponseEntity<?> updateRelationship(@PathVariable Long relationshipId, @RequestBody UpdateCastRelationshipRequest updateCastRelationshipRequest) {
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

}
