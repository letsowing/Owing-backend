package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class UpdateFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements
    UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract BaseDndDomainService<T> baseDndDomainService();
    protected abstract BaseDndDomainService<F> fBaseDndDomainService();
    protected abstract BaseFileMapper<T, F> dndMapper();

    @Transactional
    public void executeUpdateTitle(Long id, UpdateFileTitleRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T newEntity = dndMapper().toEntity(dto);
        T updatedEntity = baseDndDomainService().updateName(entity, newEntity);
    }

    @Transactional
    public void executeUpdatePosition(Long id, UpdateFilePositionRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T beforeEntity = baseDndDomainService().getOptionalEntity(dto.beforeId());
        T afterEntity = baseDndDomainService().getOptionalEntity(dto.afterId());
        F folder = fBaseDndDomainService().getOptionalEntity(dto.folderId());
        T updatedEntity = baseDndDomainService().updateEntityPosition(entity, beforeEntity, afterEntity, folder);
    }

}
