package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class CreateFileUseCase<T extends DndFile, F extends DndFolder>
    implements CreateDndUseCase<AddFileRequest>{

    protected abstract MemberUtils memberUtils();
    protected abstract DndDomainService<T> baseDndDomainService();
    protected abstract BaseFileMapper<T, F> dndMapper();
    protected abstract DndAdapter<F> folderAdapter();

    @Transactional("jpaTransactionManager")
    public DndInfoResponse execute(AddFileRequest dto) {
        F folder = folderAdapter().findById(dto.folderId());
        T entity = dndMapper().toEntity(dto, folder);
        entity = baseDndDomainService().createEntity(entity);
        return dndMapper().toInfoResponse(entity);
    }
}
