package com.owing.api.dnd.folder.service;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;
import org.springframework.transaction.annotation.Transactional;

public abstract class UpdateFolderUseCase<T extends BaseFolder> implements
    UpdateDndUseCase<UpdateFolderTitleRequest, UpdateFolderPositionRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract BaseDndDomainService<T> baseDndDomainService();
    protected abstract BaseFolderMapper<T> dndMapper();

    @Transactional("jpaTransactionManager")
    public void executeUpdateTitle(Long id, UpdateFolderTitleRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T newEntity = dndMapper().toEntity(dto);
        T updatedEntity = baseDndDomainService().updateName(entity, newEntity);
    }

    @Transactional("jpaTransactionManager")
    public void executeUpdatePosition(Long id, UpdateFolderPositionRequest dto) {
        T entity = baseDndDomainService().getEntity(id);
        T beforeEntity = baseDndDomainService().getOptionalEntity(dto.beforeId());
        T afterEntity = baseDndDomainService().getOptionalEntity(dto.afterId());
        T updatedEntity = baseDndDomainService().updateEntityPosition(entity, beforeEntity, afterEntity);
    }

}
