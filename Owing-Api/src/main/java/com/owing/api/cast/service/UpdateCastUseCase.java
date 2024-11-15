package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateCastUseCase extends UpdateFileUseCase<CastNode, CastFolderNode> {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final MemberUtils memberUtils;
    private final CastFolderNodeDomainService castFolderNodeDomainService;

    @Transactional
    public void executeUpdateInfo(Long castId, UpdateCastInfoRequest updateCastInfoRequest) {
        CastNode castNode = castNodeDomainService.getEntity(castId);

        CastNodeInfo castNodeInfo = castNodeMapper.toCastNodeInfo(updateCastInfoRequest);
        castNodeDomainService.updateCastNodeInfo(castNode, castNodeInfo);

        if (!updateCastInfoRequest.folderId().equals(castNode.getParentId())) {
            CastFolderNode attachCandidateFolder = castFolderNodeDomainService.getEntity(updateCastInfoRequest.folderId());

            // detach current folder
            castNodeDomainService.detachFolder(castNode, castNode.getParentId());

            // update file position, update folder(parent)
            CastNode lastCastNode = castNodeDomainService.getLastPositionCastNodeInFolder(attachCandidateFolder.getId());
            castNodeDomainService.updateEntityPosition(castNode, lastCastNode, null, attachCandidateFolder);

            // attach candidate folder
            castNodeDomainService.attachFolder(castNode, castNode.getParentId());
        }
    }

    public void executeUpdateCoordinate(Long castId, UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        CastNode castNode = castNodeDomainService.getEntity(castId);

        Coordinate coordinate = castNodeMapper.toCoordinate(updateCastCoordinateRequest);
        castNodeDomainService.updateCastNodeCoordinate(castNode, coordinate);
    }

    // Bean Setting
    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected BaseDndDomainService<CastNode> baseDndDomainService() {
        return this.castNodeDomainService;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> fBaseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

    @Override
    protected BaseFileMapper<CastNode, CastFolderNode> dndMapper() {
        return this.castNodeMapper;
    }
}
