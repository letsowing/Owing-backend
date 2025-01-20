package com.owing.api.cast.service.folder;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastFolderUseCase extends DeleteFolderUseCase<CastFolderNode> {

    private final CastFolderNodeService castFolderNodeDomainService;
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
        folderService().delete(castFolderNode);
    }

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndService<CastFolderNode> folderService() {
        return this.castFolderNodeDomainService;
    }

    @Override
    protected DndAdapter<CastFolderNode> folderAdapter() {
        return castFolderNodeAdapter;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
