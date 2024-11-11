package com.owing.api.dnd.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;

import io.swagger.v3.oas.annotations.Operation;

public abstract class DndController<A, U, P>{

    protected abstract CreateDndUseCase<?, A> createDndUseCase();
    protected abstract ReadDndUseCase<?, ?> readDndUseCase();
    protected abstract DeleteDndUseCase deleteDndUseCase();
    protected abstract UpdateDndUseCase<U, P> updateDndUseCase();

    @GetMapping("/{id}")
    @Operation(summary = "✨ 일반: 파일 or 폴더 상세 조회", description = "파일 or 폴더 상세조회")
    public ResponseEntity<?> getDnd(@PathVariable Long id) {
        return ResponseEntity.ok(readDndUseCase().executeRetrieve(id));
    }

    @PostMapping("/dnd")
    @Operation(summary = "✨ DnD: 파일 or 폴더 생성", description = "폴더탭에서 파일이나 폴더를 생성합니다. DnD 생성은 Title만 받습니다.")
    public ResponseEntity<?> createDnd(@RequestBody A addDndRequest) {
        return ResponseEntity.ok(createDndUseCase().execute(addDndRequest));
    }

    @PatchMapping("/{id}/title")
    @Operation(summary = "✨ DnD: 파일 or 폴더 이름 수정", description = "폴더탭에서 파일이나 폴더의 이름을 변경합니다.")
    public ResponseEntity<Void> updateDndTitle(@PathVariable Long id, @RequestBody U updateDndRequest) {
        updateDndUseCase().executeUpdateTitle(id, updateDndRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/position")
    @Operation(summary = "✨ DnD: 파일 or 폴더 위치 이동", description = "폴더탭에서 파일이나 폴더의 위치를 이동합니다.")
    public ResponseEntity<Void> updateDndPosition(@PathVariable Long id, @RequestBody P updateDndPositionRequest) {
        updateDndUseCase().executeUpdatePosition(id, updateDndPositionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "✨ DnD: 파일 or 폴더 삭제", description = "폴더탭에서 파일이나 폴더를 삭제합니다.")
    public ResponseEntity<Void> deleteDnd(@PathVariable Long id) {
        deleteDndUseCase().execute(id);
        return ResponseEntity.noContent().build();
    }
}
