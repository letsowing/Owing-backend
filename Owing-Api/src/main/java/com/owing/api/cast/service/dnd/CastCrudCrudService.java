package com.owing.api.cast.service.dnd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastInfoWithFolderResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CastCrudCrudService extends DndFileCrudService<CastNode, CastFolderNode> {
	private final CastNodeService castNodeDomainService;
	private final CastNodeMapper castNodeMapper;
	private final CastFolderNodeAdapter castFolderNodeAdapter;
	private final MemberUtils memberUtils;
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanMapper trashCanMapper;
	private final CastNodeAdapter castNodeAdapter;

	public CastInfoWithFolderResponse get(Long castId){
		CastNode castnode = castNodeAdapter.findById(castId);
		return castNodeMapper.toInfoWithFolderResponse(castnode);
	}

	public CastInfoResponse executeFull(CreateCastRequest createCastRequest) {
		CastNode castNode = castNodeMapper.toEntity(createCastRequest);
		CastFolderNode castFolderNode = castFolderNodeAdapter.findById(createCastRequest.folderId());

		castNode.connectFolder(castFolderNode);
		CastNode savedCastNode = castNodeDomainService.create(castNode);
		return castNodeMapper.toInfoResponse( savedCastNode);
	}

	// relationship 수정을 위한 재정의
	@Override
	@Transactional
	public void updatePosition(Long id, UpdateFilePositionRequest dto) {
		CastNode entity = castNodeAdapter.findById(id);
		CastNode beforeEntity = castNodeAdapter.findById(dto.beforeId());
		CastNode afterEntity = castNodeAdapter.findById(dto.afterId());
		CastFolderNode folder = castFolderNodeAdapter.findById(dto.folderId());

		castNodeDomainService.detachFolder(entity, entity.getParentId());
		castNodeDomainService.updatePosition(entity, beforeEntity, afterEntity, folder);
		castNodeDomainService.attachFolder(entity, entity.getParentId());
	}


	@Override
	protected MemberUtils memberUtils() {
		return this.memberUtils;
	}

	@Override
	protected DndService<CastNode> fileService() {
		return this.castNodeDomainService;
	}

	@Override
	protected BaseFileMapper<CastNode, CastFolderNode> fileMapper() {
		return this.castNodeMapper;
	}

	@Override
	protected DndAdapter<CastFolderNode> folderAdapter() {
		return castFolderNodeAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}

	@Override
	protected TrashCanMapper trashCanMapper() {
		return trashCanMapper;
	}

	@Override
	protected DndAdapter<CastNode> fileAdapter() {
		return castNodeAdapter;
	}

}
