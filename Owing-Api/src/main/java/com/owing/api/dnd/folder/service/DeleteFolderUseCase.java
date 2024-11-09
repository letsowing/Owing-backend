package com.owing.api.dnd.folder.service;

import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeleteFolderUseCase<T extends BaseFolder> implements DeleteDndUseCase {
    protected final MemberUtils memberUtils;
    protected final BaseDndDomainService<T> baseDndDomainService;

    @Transactional
    public void execute(Long dndId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = baseDndDomainService.getEntity(dndId);
        baseDndDomainService.deleteEntity(entity);
    }

}
