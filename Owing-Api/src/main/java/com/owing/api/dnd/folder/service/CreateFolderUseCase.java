package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.base.model.DndFolder;

public abstract class CreateFolderUseCase<T extends DndFolder> implements
    CreateDndUseCase<AddFolderRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract DndService<T> folderService();
    protected abstract BaseFolderMapper<T> folderMapper();

    @Transactional("jpaTransactionManager")
    public FolderInfoResponse execute(AddFolderRequest dto) {
        T entity = folderMapper().toEntity(dto);
        entity = folderService().create(entity);
        return folderMapper().toInfoResponse(entity);
    }
}
