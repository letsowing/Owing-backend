package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.entity.dnd.base.service.BaseDndDomainService;
import com.owing.entity.dnd.folder.model.BaseFolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UpdateFolderUseCase<T extends BaseFolder> implements
    UpdateDndUseCase<FolderInfoResponse, UpdateFolderRequest, UpdateFolderPositionRequest> {
    protected final MemberUtils memberUtils;
    protected final BaseDndDomainService<T> baseDndDomainService;
    protected final BaseFolderMapper<T> dndMapper;

    @Transactional
    public FolderInfoResponse execute(Long id, UpdateFolderRequest dto) {
        T entity = baseDndDomainService.getEntity(id);
        T newEntity = dndMapper.toEntity(dto);
        T updatedEntity = baseDndDomainService.updateTitle(entity, newEntity);
        return dndMapper.toInfoResponse(updatedEntity);
    }

    @Transactional
    public FolderInfoResponse executeUpdatePosition(Long id, UpdateFolderPositionRequest dto) {
        T entity = baseDndDomainService.getEntity(id);
        T updatedEntity = baseDndDomainService.updateEntityPosition(entity, dto.position());
        return dndMapper.toInfoResponse(updatedEntity);
    }

}
