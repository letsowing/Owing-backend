package com.owing.api.cast.service.file;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.service.CastNodeService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastUseCase extends UpdateFileUseCase<CastNode, CastFolderNode> {

    private final CastNodeService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final MemberUtils memberUtils;
    private final CastNodeAdapter castNodeAdapter;
    private final CastFolderNodeAdapter castFolderNodeAdapter;

    @Transactional("neo4jTransactionManager")
    public void executeUpdateInfo(Long castId, UpdateCastInfoRequest updateCastInfoRequest) {
        CastNode castNode = castNodeAdapter.findById(castId);

        CastNodeInfo castNodeInfo = castNodeMapper.toCastNodeInfo(updateCastInfoRequest);
        castNodeDomainService.updateCastNodeInfo(castNode, castNodeInfo);

        if (!updateCastInfoRequest.folderId().equals(castNode.getParentId())) {
            CastFolderNode attachCandidateFolder = castFolderNodeAdapter.findById(updateCastInfoRequest.folderId());

            // detach current folder
            castNodeDomainService.detachFolder(castNode, castNode.getParentId());

            // update file position, update folder(parent)
            CastNode lastCastNode = castNodeDomainService.getLastPositionCastNodeInFolder(attachCandidateFolder.getId());
            castNodeDomainService.updatePosition(castNode, lastCastNode, null, attachCandidateFolder);

            // attach candidate folder
            castNodeDomainService.attachFolder(castNode, castNode.getParentId());
        }
    }

    public void executeUpdateCoordinate(Long castId, UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        CastNode castNode = castNodeAdapter.findById(castId);

        Coordinate coordinate = castNodeMapper.toCoordinate(updateCastCoordinateRequest);
        castNodeDomainService.updateCastNodeCoordinate(castNode, coordinate);
    }

    // relationship 수정을 위한 재정의
    @Override
    @Transactional("neo4jTransactionManager")
    public void executeUpdatePosition(Long id, UpdateFilePositionRequest dto) {
        CastNode entity = castNodeAdapter.findById(id);
        CastNode beforeEntity = castNodeAdapter.findById(dto.beforeId());
        CastNode afterEntity = castNodeAdapter.findById(dto.afterId());
        CastFolderNode folder = castFolderNodeAdapter.findById(dto.folderId());

        castNodeDomainService.detachFolder(entity, entity.getParentId());
        castNodeDomainService.updatePosition(entity, beforeEntity, afterEntity, folder);
        castNodeDomainService.attachFolder(entity, entity.getParentId());
    }

    // Bean Setting
    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndService<CastNode> fileService() {
        return castNodeDomainService;
    }

    @Override
    protected BaseFileMapper<CastNode, CastFolderNode> fileMapper() {
        return castNodeMapper;
    }

    @Override
    protected DndAdapter<CastNode> fileAdapter() {
        return castNodeAdapter;
    }

    @Override
    protected DndAdapter<CastFolderNode> folderAdapter() {
        return castFolderNodeAdapter;
    }
}
