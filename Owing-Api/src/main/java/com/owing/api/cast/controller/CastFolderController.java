package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.service.CreateCastFolderUseCase;
import com.owing.api.cast.service.DeleteCastFolderUseCase;
import com.owing.api.cast.service.ReadCastFolderUseCase;
import com.owing.api.cast.service.UpdateCastFolderUseCase;
import com.owing.api.dnd.base.controller.BaseFolderController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.node.folder.cast.model.CastFolderNode;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
@Tag(name = "캐릭터 폴더 /cast/folders", description = "캐릭터 폴더 API")
public class CastFolderController extends BaseFolderController {

    private final CreateCastFolderUseCase createCastFolderUseCase;
    private final ReadCastFolderUseCase readCastFolderUseCase;
    private final UpdateCastFolderUseCase updateCastFolderUseCase;
    private final DeleteCastFolderUseCase deleteCastFolderUseCase;

    // TODO BaseFolderController의 공통 구현으로 분리
    @GetMapping("/{projectId}/dropdown")
    public ResponseEntity<List<CastFolderDropdownItemResponse>> getFolderDropdownList(@PathVariable Long projectId) {
        return ResponseEntity.ok(readCastFolderUseCase.executeDropdownList(projectId));
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<Void> updateCastFolderInfo(@PathVariable Long folderId, @Valid @RequestBody UpdateCastFolderInfo updateCastFolderInfo) {
        updateCastFolderUseCase.executeInfoUpdate(folderId, updateCastFolderInfo);
        return ResponseEntity.noContent().build();
    }

    // Bean Setting
    @Override
    protected CreateDndUseCase<AddFolderRequest> createDndUseCase() {
        return this.createCastFolderUseCase;
    }

    @Override
    protected ReadFolderUseCase<CastFolderNode> readDndUseCase() {
        return this.readCastFolderUseCase;
    }

    @Override
    protected DeleteDndUseCase deleteDndUseCase() {
        return this.deleteCastFolderUseCase;
    }

    @Override
    protected UpdateDndUseCase<UpdateFolderTitleRequest, UpdateFolderPositionRequest> updateDndUseCase() {
        return this.updateCastFolderUseCase;
    }
}
