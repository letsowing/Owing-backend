package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.base.service.DndDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeleteFolderUseCase<T extends BaseFolderEntity> implements DeleteDndUseCase {
    protected final MemberUtils memberUtils;
    protected final DndDomainService<T> dndDomainService;

    @Transactional
    public void execute(Long dndId) {
        Long memberId = memberUtils.getCurrentMemberId();
        T entity = dndDomainService.getEntity(dndId);
        dndDomainService.deleteEntity(entity);
    }

}
