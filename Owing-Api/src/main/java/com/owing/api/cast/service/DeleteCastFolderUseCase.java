package com.owing.api.cast.service;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastFolderUseCase extends DeleteFolderUseCase<CastFolderNode> {

    private final CastFolderNodeDomainService castFolderNodeDomainService;
    private final TrashCanFolderDomainService trashCanFolderDomainService;
    private final TrashCanFolderMapper trashCanFolderMapper;
    private final ProjectAdapter projectAdapter;
    private final MemberUtils memberUtils;
    private final CastFolderNodeAdapter castFolderNodeAdapter;

    @Override
    public void execute(Long folderId) {
        // Long memberId = memberUtils.getCurrentMemberId();
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
        Project project = projectAdapter().findById(castFolderNode.getProjectId());
        trashCanFolderDomainService().createTrashCanFolder(trashCanFolderMapper().toFolderEntity(castFolderNode, project));
        baseDndDomainService().deleteEntity(castFolderNode);
    }

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
