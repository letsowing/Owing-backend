package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class CreateFolderUseCase<T extends BaseFolder> implements
    CreateDndUseCase<FolderInfoResponse, AddFolderRequest> {
    protected abstract MemberUtils memberUtils();
    protected abstract BaseDndDomainService<T> baseDndDomainService();
    protected abstract BaseFolderMapper<T> dndMapper();

    @Transactional
    public FolderInfoResponse execute(AddFolderRequest dto) {
        T entity = dndMapper().toEntity(dto);
        entity = baseDndDomainService().createEntity(entity);
        return dndMapper().toInfoResponse(entity);
    }
}
