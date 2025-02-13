package com.owing.api.cast.service.dnd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.dto.response.CastInfoWithFolderResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastDndService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CastCrudService extends DndFileCrudService<CastNode, CastFolderNode> {
	private final CastNodeMapper castNodeMapper;
	private final CastFolderNodeAdapter castFolderNodeAdapter;
	private final MemberUtils memberUtils;
	private final TrashCanDomainService trashCanDomainService;
	private final CastNodeAdapter castNodeAdapter;
	private final CastDndService castDndService;

	public CastInfoWithFolderResponse get(Long castId){
		CastNode castnode = castNodeAdapter.findById(castId);
		return castNodeMapper.toInfoWithFolderResponse(castnode);
	}

	@Transactional("neo4jTransactionManager")
	public void delete(Long fileId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		CastNode entity = castNodeAdapter.findByIdWithPjt(fileId);
		trashCanDomainService().trash(entity);
		orderService().delete(entity);
	}


	@Override
	protected MemberUtils memberUtils() {
		return this.memberUtils;
	}

	@Override
	protected DndService<CastNode> orderService() {
		return castDndService;
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
	protected DndAdapter<CastNode> fileAdapter() {
		return castNodeAdapter;
	}

}
