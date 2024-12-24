package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DeleteFileUseCase<T extends DndFile> implements DeleteDndUseCase {
    protected abstract MemberUtils memberUtils();
    protected abstract DndDomainService<T> baseDndDomainService();
    protected abstract TrashCanDomainService trashCanDomainService();
    protected abstract TrashCanMapper trashCanMapper();

    @Transactional("jpaTransactionManager")
    public void execute(Long dndId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = baseDndDomainService().getEntity(dndId);
        trashCanDomainService().createTrashCan(trashCanMapper().toEntity(entity));
        baseDndDomainService().deleteEntity(entity);
    }

}
