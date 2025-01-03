package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class CreateFileUseCase<T extends BaseFile<F>, F extends BaseFolder>
    implements CreateDndUseCase<AddFileRequest>{

    protected abstract MemberUtils memberUtils();
    protected abstract BaseDndDomainService<T> baseDndDomainService();
    protected abstract BaseFileMapper<T, F> dndMapper();
    protected abstract BaseDndAdapter<F> folderAdapter();

    @Transactional("jpaTransactionManager")
    public DndInfoResponse execute(AddFileRequest dto) {
        F folder = folderAdapter().findById(dto.folderId());
        T entity = dndMapper().toEntity(dto, folder);
        entity = baseDndDomainService().createEntity(entity);
        return dndMapper().toInfoResponse(entity);
    }
}
