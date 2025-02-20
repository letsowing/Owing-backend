package com.owing.api.dnd.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;
import com.owing.api.dnd.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DndFolderCrudService<T extends DndFolder> {
	protected abstract DndService<T> dndService();
	protected abstract DndAdapter<T> folderAdapter();
	protected abstract DndFolderMapper<T> folderMapper();
	protected abstract TrashCanDomainService trashCanDomainService();

	public FolderInfoResponse get(Long folderId){
		T entity = folderAdapter().findById(folderId);
		return folderMapper().toInfoResponse(entity);
	}

	public FolderInfoListResponse getList(Long projectId) {
		List<T> entityList = folderAdapter().findAllByParentId(projectId);
		return folderMapper().toListResponse(entityList);
	}

	@Transactional("jpaTransactionManager")
	public FolderInfoResponse create(AddFolderRequest dto) {
		T entity = folderMapper().toEntity(dto);
		entity = dndService().create(entity);
		return folderMapper().toInfoResponse(entity);
	}

	@Transactional("jpaTransactionManager")
	public void updateName(Long folderId, UpdateFolderNameRequest dto) {
		T entity = folderAdapter().findById(folderId);
		entity.updateName(dto.name());
		folderAdapter().updateName(entity);
	}

	@Transactional("jpaTransactionManager")
	public void updatePosition(Long folderId, UpdateFolderPositionRequest dto) {
		T entity = folderAdapter().findById(folderId);
		T beforeEntity = dto.beforeId() == null ? null : folderAdapter().findById(dto.beforeId());
		T afterEntity = dto.afterId() == null ? null : folderAdapter().findById(dto.afterId());
		T updatedEntity = dndService().updatePosition(entity, beforeEntity, afterEntity, null);
	}

	@Transactional("jpaTransactionManager")
	public void delete(Long folderId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		T entity = folderAdapter().findById(folderId);
		dndService().delete(entity);
		trashCanDomainService().trash(entity);
	}
}
