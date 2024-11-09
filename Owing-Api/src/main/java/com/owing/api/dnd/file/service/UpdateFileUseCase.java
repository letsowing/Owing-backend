package com.owing.api.dnd.file.service;

import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UpdateFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements
    UpdateDndUseCase<FileInfoResponse, UpdateFileRequest, UpdateFilePositionRequest> {
    protected final MemberUtils memberUtils;
    protected final BaseDndDomainService<T> baseDndDomainService;
    protected final BaseFileMapper<T, F> dndMapper;

    @Transactional
    public FileInfoResponse execute(Long id, UpdateFileRequest dto) {
        T entity = baseDndDomainService.getEntity(id);
        T newEntity = dndMapper.toEntity(dto);
        T updatedEntity = baseDndDomainService.updateTitle(entity, newEntity);
        return dndMapper.toInfoResponse(updatedEntity);
    }

    @Transactional
    public FileInfoResponse executeUpdatePosition(Long id, UpdateFilePositionRequest dto) {
        T entity = baseDndDomainService.getEntity(id);
        T updatedEntity = baseDndDomainService.updateEntityPosition(entity, dto.position());
        return dndMapper.toInfoResponse(updatedEntity);
    }

}
