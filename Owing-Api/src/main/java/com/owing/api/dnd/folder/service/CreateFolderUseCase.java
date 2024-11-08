package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class CreateFolderUseCase<T extends BaseFolderEntity> implements
    CreateDndUseCase<FolderInfoResponse, AddFolderRequest> {
    protected final MemberUtils memberUtils;
    protected final DndDomainService<T> dndDomainService;
    protected final BaseFolderMapper<T> dndMapper;

    @Transactional
    public FolderInfoResponse execute(AddFolderRequest dto) {
        T entity = dndMapper.toEntity(dto);
        entity = dndDomainService.createEntity(entity);
        return dndMapper.toInfoResponse(entity);
    }
}
