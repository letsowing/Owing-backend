package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.entity.dnd.base.adapter.DndAdapter;
import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class CreateFileUseCase<T extends BaseFileEntity<F>, F extends BaseFolderEntity> implements
    CreateDndUseCase<FileInfoResponse, AddFileRequest>{
    protected final MemberUtils memberUtils;
    protected final DndDomainService<T> dndDomainService;
    protected final BaseFileMapper<T, F> dndMapper;
    protected final DndAdapter<F> folderAdapter;

    @Transactional
    public FileInfoResponse execute(AddFileRequest dto) {
        F folder = folderAdapter.findById(dto.folderId());
        T entity = dndMapper.toEntity(dto, folder);
        System.out.println(entity.getParentId());
        System.out.println(entity.getFolder().getId());
        entity = dndDomainService.createEntity(entity);
        return dndMapper.toInfoResponse(entity);
    }
}
