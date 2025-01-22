package com.owing.api.dnd.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;

public abstract class DndFolderCrudService<T extends DndFolder> implements DndCrudService<AddFolderRequest, UpdateFolderTitleRequest, UpdateFolderPositionRequest> {
	protected abstract MemberUtils memberUtils();
	protected abstract DndService<T> folderService();
	protected abstract BaseFolderMapper<T> folderMapper();
	protected abstract DndAdapter<T> folderAdapter();
	protected abstract TrashCanFolderAdaptor trashCanFolderAdaptor();
	protected abstract ProjectAdapter projectAdapter();
	protected abstract TrashCanFolderMapper trashCanFolderMapper();

	@Transactional
	public FolderInfoResponse create(AddFolderRequest dto) {
		T entity = folderMapper().toEntity(dto);
		entity = folderService().create(entity);
		return folderMapper().toInfoResponse(entity);
	}

	@Transactional
	public void delete(Long folderId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		T entity = folderAdapter().findById(folderId);
		Project project = projectAdapter().findById(entity.getProjectId());
		trashCanFolderAdaptor().save(trashCanFolderMapper().toFolderEntity(entity, project));
		folderService().delete(entity);
	}

	public FolderInfoResponse get(Long folderId){
		T entity = folderAdapter().findById(folderId);
		return folderMapper().toInfoResponse(entity);
	}

	public FolderInfoListResponse getList(Long projectId) {
		List<T> entityList = folderAdapter().findAllByParentId(projectId);
		return folderMapper().toListResponse(entityList);
	}

	@Transactional
	public void updateTitle(Long folderId, UpdateFolderTitleRequest dto) {
		T entity = folderAdapter().findById(folderId);
		T newEntity = folderMapper().toEntity(dto);
		T updatedEntity = folderService().update(entity, newEntity);
	}

	@Transactional
	public void updatePosition(Long folderId, UpdateFolderPositionRequest dto) {
		T entity = folderAdapter().findById(folderId);
		T beforeEntity = dto.beforeId() == null ? null : folderAdapter().findById(dto.beforeId());
		T afterEntity = dto.afterId() == null ? null : folderAdapter().findById(dto.afterId());
		T updatedEntity = folderService().updatePosition(entity, beforeEntity, afterEntity, null);
	}
}
