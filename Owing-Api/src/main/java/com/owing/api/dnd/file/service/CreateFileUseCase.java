package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.core.dnd.base.service.DndService;

public abstract class CreateFileUseCase<T extends DndFile, F extends DndFolder>
    implements CreateDndUseCase<AddFileRequest>{

    protected abstract MemberUtils memberUtils();
    protected abstract DndService<T> fileService();
    protected abstract BaseFileMapper<T, F> fileMapper();
    protected abstract DndAdapter<F> folderAdapter();

    @Transactional("jpaTransactionManager")
    public DndInfoResponse execute(AddFileRequest dto) {
        F folder = folderAdapter().findById(dto.folderId());
        T entity = fileMapper().toEntity(dto, folder);
        entity = fileService().create(entity);
        return fileMapper().toInfoResponse(entity);
    }
}
