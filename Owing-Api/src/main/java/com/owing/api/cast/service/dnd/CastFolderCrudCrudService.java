package com.owing.api.cast.service.dnd;

import java.util.List;

import org.springframework.stereotype.Service;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.dnd.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CastFolderCrudCrudService extends DndFolderCrudService<CastFolderNode> {

	private final ProjectNodeAdapter projectNodeAdapter;
	private final CastFolderNodeMapper castFolderNodeMapper;
	private final CastFolderNodeService castFolderDomainService;
	private final MemberUtils memberUtils;
	private final CastFolderNodeService castFolderNodeDomainService;
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanFolderMapper trashCanFolderMapper;
	private final ProjectAdapter projectAdapter;
	private final CastFolderNodeAdapter castFolderNodeAdapter;

	@Override
	public FolderInfoResponse get(Long folderId) {
		CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
		return castFolderNodeMapper.toInfoResponse(castFolderNode);
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
	public FolderInfoResponse create(AddFolderRequest createCastFolderRequest) {
		ProjectNode projectNode = projectNodeAdapter.findById(createCastFolderRequest.projectId());
		CastFolderNode castFolderNode = castFolderNodeMapper.toEntity(
			createCastFolderRequest.name());

		castFolderNode.connectProject(projectNode);
		CastFolderNode savedCastFolder = castFolderDomainService.create(castFolderNode);

		return castFolderNodeMapper.toInfoResponse(savedCastFolder);
	}

	public void executeInfoUpdate(Long folderId, UpdateCastFolderInfo updateCastFolderInfo) {
		CastFolderNode castFolderNode = castFolderNodeAdapter.findById(folderId);
		castFolderNodeDomainService.updateCastFolderNodeInfo(
			castFolderNode,
			updateCastFolderInfo.name(),
			updateCastFolderInfo.description()
		);
	}

	@Override
	public void delete(Long folderId) {
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

	protected BaseFolderMapper<CastFolderNode> folderMapper() {
		return this.castFolderNodeMapper;
	}

}
