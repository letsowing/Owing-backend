package com.owing.api.dnd.folder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

public abstract class DeleteFolderUseCase<T extends BaseFolder> implements DeleteDndUseCase {
    protected abstract MemberUtils memberUtils();
    protected abstract BaseDndDomainService<T> baseDndDomainService();
    protected abstract TrashCanFolderDomainService trashCanFolderDomainService();
    protected abstract ProjectAdapter projectAdapter();
    protected abstract TrashCanMapper trashCanMapper();

    @Transactional
    public void execute(Long dndId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        T entity = baseDndDomainService().getEntity(dndId);
        Project project = projectAdapter().findById(entity.getProjectId());
        trashCanFolderDomainService().createTrashCanFolder(trashCanMapper().toFolderEntity(entity, project));
        baseDndDomainService().deleteEntity(entity);
    }

}
