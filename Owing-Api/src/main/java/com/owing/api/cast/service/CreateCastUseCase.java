package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastDndService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastUseCase {
	private final CastDndService castDndService;
	private final CastNodeMapper castNodeMapper;
	private final CastFolderNodeAdapter castFolderNodeAdapter;

	public CastInfoResponse executeFull(CreateCastRequest createCastRequest) {
		CastNode castNode = castNodeMapper.toEntity(createCastRequest);
		CastFolderNode castFolderNode = castFolderNodeAdapter.findById(createCastRequest.folderId());

		castNode.connectFolder(castFolderNode);
		CastNode savedCastNode = castDndService.create(castNode);
		return castNodeMapper.toInfoResponse(savedCastNode);
	}
}
