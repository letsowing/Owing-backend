package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class UpdateFileUseCase<T extends DndFile, F extends DndFolder> implements
    UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract DndDomainService<T> baseDndDomainService();
    protected abstract DndDomainService<F> fBaseDndDomainService();
    protected abstract BaseFileMapper<T, F> dndMapper();

    @Transactional("jpaTransactionManager")
    public void executeUpdateTitle(Long id, UpdateFileTitleRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T newEntity = dndMapper().toEntity(dto);
        T updatedEntity = baseDndDomainService().updateName(entity, newEntity);
    }

    @Transactional("jpaTransactionManager")
    public void executeUpdatePosition(Long id, UpdateFilePositionRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T beforeEntity = baseDndDomainService().getOptionalEntity(dto.beforeId());
        T afterEntity = baseDndDomainService().getOptionalEntity(dto.afterId());
        F folder = fBaseDndDomainService().getOptionalEntity(dto.folderId());
        T updatedEntity = baseDndDomainService().updateEntityPosition(entity, beforeEntity, afterEntity, folder);
    }

}
