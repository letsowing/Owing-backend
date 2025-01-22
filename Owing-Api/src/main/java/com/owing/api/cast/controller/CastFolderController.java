package com.owing.api.cast.controller;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.service.dnd.CastFolderCrudCrudService;
import com.owing.api.dnd.controller.BaseFolderController;
import com.owing.api.dnd.service.DndFolderCrudService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
@Tag(name = "캐릭터 폴더 /cast/folders", description = "캐릭터 폴더 API")
public class CastFolderController extends BaseFolderController {

    private final CastFolderCrudCrudService castFolderCrudService;

    // TODO BaseFolderController의 공통 구현으로 분리
    @GetMapping("/{projectId}/dropdown")
    public ResponseEntity<List<CastFolderDropdownItemResponse>> getFolderDropdownList(@PathVariable Long projectId) {
        return ResponseEntity.ok(castFolderCrudService.executeDropdownList(projectId));
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<Void> updateCastFolderInfo(@PathVariable Long folderId, @Valid @RequestBody UpdateCastFolderInfo updateCastFolderInfo) {
        castFolderCrudService.executeInfoUpdate(folderId, updateCastFolderInfo);
        return ResponseEntity.noContent().build();
    }

    // Bean Setting
    @Override
    protected DndFolderCrudService dndCrudService() {
        return castFolderCrudService;
    }

}
