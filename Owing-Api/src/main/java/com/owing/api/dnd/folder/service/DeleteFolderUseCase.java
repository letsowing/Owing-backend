package com.owing.api.dnd.folder.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;

public abstract class DeleteFolderUseCase<T extends DndFolder> implements DeleteDndUseCase {
    protected abstract MemberUtils memberUtils();
    protected abstract DndService<T> folderService();
    protected abstract DndAdapter<T> folderAdapter();
    protected abstract TrashCanFolderDomainService trashCanFolderDomainService();
    protected abstract ProjectAdapter projectAdapter();
    protected abstract TrashCanFolderMapper trashCanFolderMapper();

    @Transactional
    public void execute(Long folderId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = folderAdapter().findById(folderId);
        Project project = projectAdapter().findById(entity.getProjectId());
        trashCanFolderDomainService().createTrashCanFolder(trashCanFolderMapper().toFolderEntity(entity, project));
        folderService().delete(entity);
    }

}
