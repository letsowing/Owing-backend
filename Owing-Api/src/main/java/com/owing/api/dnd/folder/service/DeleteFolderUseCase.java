package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.core.dnd.folder.model.DndFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;

public abstract class DeleteFolderUseCase<T extends DndFolder> implements DeleteDndUseCase {
    protected abstract MemberUtils memberUtils();
    protected abstract DndDomainService<T> baseDndDomainService();
    protected abstract TrashCanFolderDomainService trashCanFolderDomainService();
    protected abstract ProjectAdapter projectAdapter();
    protected abstract TrashCanFolderMapper trashCanFolderMapper();

    @Transactional
    public void execute(Long dndId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = baseDndDomainService().getEntity(dndId);
        Project project = projectAdapter().findById(entity.getProjectId());
        trashCanFolderDomainService().createTrashCanFolder(trashCanFolderMapper().toFolderEntity(entity, project));
        baseDndDomainService().deleteEntity(entity);
    }

}
