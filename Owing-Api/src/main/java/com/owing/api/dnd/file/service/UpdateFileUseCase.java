package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;

public abstract class UpdateFileUseCase<T extends DndFile, F extends DndFolder> implements
    UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract DndService<T> fileService();
    protected abstract BaseFileMapper<T, F> fileMapper();
    protected abstract DndAdapter<T> fileAdapter();
    protected abstract DndAdapter<F> folderAdapter();

	@Transactional("jpaTransactionManager")
    public void executeUpdateTitle(Long fileId, UpdateFileTitleRequest dto) {
        T entity = fileAdapter().findById(fileId);
        T newEntity = fileMapper().toEntity(dto);
        T updatedEntity = fileService().update(entity, newEntity);
    }

	@Transactional("jpaTransactionManager")
    public void executeUpdatePosition(Long fileId, UpdateFilePositionRequest dto) {
        T entity = fileAdapter().findById(fileId);
        T beforeEntity = dto.beforeId() == null ? null : fileAdapter().findById(dto.beforeId());
        T afterEntity = dto.afterId() == null ? null : fileAdapter().findById(dto.afterId());

        F folder = folderAdapter().findById(dto.folderId());

        T updatedEntity = fileService().updatePosition(entity, beforeEntity, afterEntity, folder);
    }

}
