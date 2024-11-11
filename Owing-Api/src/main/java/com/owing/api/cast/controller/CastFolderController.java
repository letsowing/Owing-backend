package com.owing.api.cast.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.service.CreateCastFolderUseCase;
import com.owing.api.cast.service.DeleteCastFolderUseCase;
import com.owing.api.cast.service.ReadCastFolderUseCase;
import com.owing.api.cast.service.UpdateCastFolderUseCase;
import com.owing.api.dnd.base.controller.BaseFolderController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
@Tag(name = "캐릭터 폴더 /cast/folders", description = "캐릭터 폴더 API")
public class CastFolderController extends BaseFolderController {

    private final CreateCastFolderUseCase createCastFolderUseCase;
    private final ReadCastFolderUseCase readCastFolderUseCase;
    private final UpdateCastFolderUseCase updateCastFolderUseCase;
    private final DeleteCastFolderUseCase deleteCastFolderUseCase;

    @PutMapping("/{folderId}")
    public ResponseEntity<Void> updateCastFolderInfo(@PathVariable Long folderId, @RequestBody UpdateCastFolderInfo updateCastFolderInfo) {
        updateCastFolderUseCase.executeInfoUpdate(folderId, updateCastFolderInfo);
        return ResponseEntity.noContent().build();
    }

    // Bean Setting
    @Override
    protected CreateDndUseCase<?, AddFolderRequest> createDndUseCase() {
        return this.createCastFolderUseCase;
    }

    @Override
    protected ReadDndUseCase<?, ?> readDndUseCase() {
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
