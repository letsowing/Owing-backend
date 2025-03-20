package com.owing.api.cast.service.dnd;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderDndService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CastFolderCrudService extends DndFolderCrudService<CastFolderNode> {

	private final ProjectNodeAdapter projectNodeAdapter;
	private final CastFolderNodeMapper castFolderNodeMapper;
	private final CastFolderDndService castFolderDndService;
	private final CastFolderNodeAdapter castFolderNodeAdapter;
	private final TrashCanDomainService trashCanDomainService;

	@Override
	public FolderInfoResponse get(Long folderId) {
		CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
		return castFolderNodeMapper.toInfoResponse(castFolderNode);
	}

	@Override
	@Transactional("neo4jTransactionManager")
	public void updateName(Long folderId, UpdateFolderNameRequest dto) {
		CastFolderNode castFolderNode = castFolderNodeAdapter.findById(folderId);
		castFolderNode.updateName(dto.name());
		castFolderNodeAdapter.updateName(castFolderNode);
	}

	@Override
	public FolderInfoListResponse getList(Long projectId) {
		ProjectNode projectNode = projectNodeAdapter.findById(projectId);
		List<CastFolderNode> castFolderNodeList = castFolderNodeAdapter.findAllWithRelationshipByProjectId(projectNode.getId());
		return castFolderNodeMapper.toListResponse(castFolderNodeList);
	}

	public List<CastFolderDropdownItemResponse> executeDropdownList(Long projectId) {
		ProjectNode projectNode = projectNodeAdapter.findById(projectId);
		List<CastFolderNode> castFolderNodeList = castFolderNodeAdapter.findAllByParentId(projectNode.getId());
		return castFolderNodeMapper.toDropdownListResponse(castFolderNodeList);
	}

	@Override
	@Transactional("neo4jTransactionManager")
	public FolderInfoResponse create(AddFolderRequest createCastFolderRequest) {
		ProjectNode projectNode = projectNodeAdapter.findById(createCastFolderRequest.projectId());
		CastFolderNode castFolderNode = castFolderNodeMapper.toEntity(
			createCastFolderRequest.name());

		castFolderNode.connectProject(projectNode);
		CastFolderNode savedCastFolder = castFolderDndService.create(castFolderNode);

		return castFolderNodeMapper.toInfoResponse(savedCastFolder);
	}


	@Override
	@Transactional("neo4jTransactionManager")
	public void delete(Long folderId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
		castFolderDndService.delete(castFolderNode);
		trashCanDomainService().trash(castFolderNode);
	}

	@Override
	protected DndService<CastFolderNode> dndService() {
		return castFolderDndService;
	}

	@Override
	protected DndAdapter<CastFolderNode> folderAdapter() {
		return castFolderNodeAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}
	protected DndFolderMapper<CastFolderNode> folderMapper() {
		return this.castFolderNodeMapper;
	}

}
