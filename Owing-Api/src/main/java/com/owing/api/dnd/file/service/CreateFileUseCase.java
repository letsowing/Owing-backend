package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.entity.dnd.base.adapter.BaseDndAdapter;
import com.owing.entity.dnd.base.service.BaseDndDomainService;
import com.owing.entity.dnd.file.model.BaseFile;
import com.owing.entity.dnd.folder.model.BaseFolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class CreateFileUseCase<T extends BaseFile<F>, F extends BaseFolder>
    implements CreateDndUseCase<FileInfoResponse, AddFileRequest>{

    protected final MemberUtils memberUtils;
    protected final BaseDndDomainService<T> baseDndDomainService;
    protected final BaseFileMapper<T, F> dndMapper;
    protected final BaseDndAdapter<F> folderAdapter;

    @Transactional
    public FileInfoResponse execute(AddFileRequest dto) {
        F folder = folderAdapter.findById(dto.folderId());
        T entity = dndMapper.toEntity(dto, folder);
        entity = baseDndDomainService.createEntity(entity);
        return dndMapper.toInfoResponse(entity);
    }
}
