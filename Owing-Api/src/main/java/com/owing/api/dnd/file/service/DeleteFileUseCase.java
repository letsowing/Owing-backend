package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DeleteFileUseCase<T extends DndFile> implements DeleteDndUseCase {
    protected abstract MemberUtils memberUtils();
    protected abstract DndAdapter<T> fileAdapter();
    protected abstract DndService<T> fileService();
    protected abstract TrashCanDomainService trashCanDomainService();
    protected abstract TrashCanMapper trashCanMapper();

	@Transactional("jpaTransactionManager")
    public void execute(Long fileId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = fileAdapter().findById(fileId);
        trashCanDomainService().createTrashCan(trashCanMapper().toEntity(entity));
        fileService().delete(entity);
    }

}
