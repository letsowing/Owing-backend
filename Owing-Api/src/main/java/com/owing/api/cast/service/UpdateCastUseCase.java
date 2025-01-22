package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.service.CastNodeService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastUseCase {
	private final CastNodeAdapter castNodeAdapter;
	private final CastNodeService castNodeDomainService;
	private final CastNodeMapper castNodeMapper;
	private final CastFolderNodeAdapter castFolderNodeAdapter;

	@Transactional
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

	@Transactional
	public void executeUpdateCoordinate(Long castId, UpdateCastCoordinateRequest updateCastCoordinateRequest) {
		CastNode castNode = castNodeAdapter.findById(castId);

		Coordinate coordinate = castNodeMapper.toCoordinate(updateCastCoordinateRequest);
		castNodeDomainService.updateCastNodeCoordinate(castNode, coordinate);
	}
}
