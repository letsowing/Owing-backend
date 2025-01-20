package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.base.model.DndFolder;

public abstract class UpdateFolderUseCase<T extends DndFolder> implements
    UpdateDndUseCase<UpdateFolderTitleRequest, UpdateFolderPositionRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract DndService<T> folderService();
    protected abstract BaseFolderMapper<T> folderMapper();
    protected abstract DndAdapter<T> folderAdapter();

    @Transactional
    public void executeUpdateTitle(Long folderId, UpdateFolderTitleRequest dto) {
        T entity = folderAdapter().findById(folderId);
        T newEntity = folderMapper().toEntity(dto);
        T updatedEntity = folderService().update(entity, newEntity);
    }

    @Transactional
    public void executeUpdatePosition(Long folderId, UpdateFolderPositionRequest dto) {
        T entity = folderAdapter().findById(folderId);
        T beforeEntity = dto.beforeId() == null ? null : folderAdapter().findById(dto.beforeId());
        T afterEntity = dto.afterId() == null ? null : folderAdapter().findById(dto.afterId());
        T updatedEntity = folderService().updatePosition(entity, beforeEntity, afterEntity, null);
    }

}
