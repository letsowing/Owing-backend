package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.base.service.DndDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UpdateFileUseCase<T extends BaseFileEntity<F>, F extends BaseFolderEntity> implements
    UpdateDndUseCase<FileInfoResponse, UpdateFileRequest, UpdateFilePositionRequest> {
    protected final MemberUtils memberUtils;
    protected final DndDomainService<T> dndDomainService;
    protected final BaseFileMapper<T, F> dndMapper;

    @Transactional
    public FileInfoResponse execute(Long id, UpdateFileRequest dto) {
        T entity = dndDomainService.getEntity(id);
        T newEntity = dndMapper.toEntity(dto);
        T updatedEntity = dndDomainService.updateEntity(entity, newEntity);
        return dndMapper.toInfoResponse(updatedEntity);
    }

    @Transactional
    public FileInfoResponse executeUpdatePosition(Long id, UpdateFilePositionRequest dto) {
        T entity = dndDomainService.getEntity(id);
        T updatedEntity = dndDomainService.updateEntityPosition(entity, dto.position());
        return dndMapper.toInfoResponse(updatedEntity);
    }

}
