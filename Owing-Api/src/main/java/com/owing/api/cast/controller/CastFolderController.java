package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.model.dto.response.CastFolderInfoResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.cast.service.CreateCastFolderUserCase;
import com.owing.api.cast.service.DeleteCastFolderUseCase;
import com.owing.api.cast.service.ReadCastFolderUserCase;
import com.owing.api.cast.service.UpdateCastFolderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
public class CastFolderController {

    private final CreateCastFolderUserCase createCastFolderUserCase;
    private final ReadCastFolderUserCase readCastFolderUserCase;
    private final UpdateCastFolderUseCase updateCastFolderUseCase;
    private final DeleteCastFolderUseCase deleteCastFolderUseCase;

    @PostMapping
    public ResponseEntity<CastFolderInfoResponse> createCastFolder(@RequestBody CreateCastFolderRequest createCastFolderRequest) {
        return ResponseEntity.ok(createCastFolderUserCase.execute(createCastFolderRequest));
    }

    @GetMapping
    public ResponseEntity<List<CastFolderResponse>> getCastFolderList(@RequestParam Long projectId) {
        return ResponseEntity.ok(readCastFolderUserCase.executeGetList(projectId));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<CastFolderResponse> getCastFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(readCastFolderUserCase.executeGetOne(folderId));
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<Void> updateCastFolderInfo(@PathVariable Long folderId, @RequestBody UpdateCastFolderInfo updateCastFolderInfo) {
        updateCastFolderUseCase.executeInfoUpdate(folderId, updateCastFolderInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteCastFolder(@PathVariable Long folderId) {
        deleteCastFolderUseCase.execute(folderId);
        return ResponseEntity.ok().build();
    }
}
