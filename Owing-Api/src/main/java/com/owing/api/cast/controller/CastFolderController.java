package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.service.CreateCastFolderUseCase;
import com.owing.api.cast.service.DeleteCastFolderUseCase;
import com.owing.api.cast.service.ReadCastFolderUseCase;
import com.owing.api.cast.service.UpdateCastFolderUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
public class CastFolderController {

    private final CreateCastFolderUseCase createCastFolderUseCase;
    private final ReadCastFolderUseCase readCastFolderUseCase;
    private final UpdateCastFolderUseCase updateCastFolderUseCase;
    private final DeleteCastFolderUseCase deleteCastFolderUseCase;

    @PostMapping
    public ResponseEntity<FolderInfoResponse> createCastFolder(@RequestBody AddFolderRequest addFolderRequest) {
        return ResponseEntity.ok(createCastFolderUseCase.execute(addFolderRequest));
    }

    @GetMapping
    public ResponseEntity<FolderInfoListResponse> getCastFolderList(@RequestParam Long projectId) {
        return ResponseEntity.ok(readCastFolderUseCase.executeList(projectId));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<FolderInfoResponse> getCastFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(readCastFolderUseCase.executeRetrieve(folderId));
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<Void> updateCastFolderInfo(@PathVariable Long folderId, @RequestBody UpdateCastFolderInfo updateCastFolderInfo) {
        updateCastFolderUseCase.executeInfoUpdate(folderId, updateCastFolderInfo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{folderId}/title")
    public ResponseEntity<Void> updateCastFolderTitle(@PathVariable Long folderId, @RequestBody UpdateFolderRequest updateFolderRequest) {
        updateCastFolderUseCase.executeUpdateTitle(folderId, updateFolderRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{folderId}")
    public ResponseEntity<Void> updateDndPosition(@PathVariable Long folderId, @RequestBody UpdateFolderPositionRequest updateDndPositionRequest) {
        updateCastFolderUseCase.executeUpdatePosition(folderId, updateDndPositionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteCastFolder(@PathVariable Long folderId) {
        deleteCastFolderUseCase.execute(folderId);
        return ResponseEntity.ok().build();
    }
}
