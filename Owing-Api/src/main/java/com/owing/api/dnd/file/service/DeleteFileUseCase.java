package com.owing.api.dnd.file.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.entity.dnd.base.service.BaseDndDomainService;
import com.owing.entity.dnd.file.model.BaseFile;
import com.owing.entity.dnd.folder.model.BaseFolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeleteFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements DeleteDndUseCase {
    protected final MemberUtils memberUtils;
    protected final BaseDndDomainService<T> baseDndDomainService;

    @Transactional
    public void execute(Long dndId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = baseDndDomainService.getEntity(dndId);
        baseDndDomainService.deleteEntity(entity);
    }

}
