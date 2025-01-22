package com.owing.api.dnd.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.model.dto.request.AddFileRequest;
import com.owing.api.dnd.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DndFileCrudService<T extends DndFile, F extends DndFolder> implements DndCrudService<AddFileRequest, UpdateFileTitleRequest, UpdateFilePositionRequest> {
	protected abstract MemberUtils memberUtils();
	protected abstract DndService<T> fileService();
	protected abstract DndAdapter<T> fileAdapter();
	protected abstract BaseFileMapper<T, F> fileMapper();
	protected abstract DndAdapter<F> folderAdapter();
	protected abstract TrashCanDomainService trashCanDomainService();
	protected abstract TrashCanMapper trashCanMapper();


	@Transactional
	public DndInfoResponse create(AddFileRequest dto) {
		F folder = folderAdapter().findById(dto.folderId());
		T entity = fileMapper().toEntity(dto, folder);
		entity = fileService().create(entity);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional
	public void delete(Long fileId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		T entity = fileAdapter().findById(fileId);
		trashCanDomainService().createTrashCan(trashCanMapper().toEntity(entity));
		fileService().delete(entity);
	}

	public DndInfoResponse get(Long fileId){
		T entity = fileAdapter().findById(fileId);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional
	public void updateTitle(Long fileId, UpdateFileTitleRequest dto) {
		T entity = fileAdapter().findById(fileId);
		T newEntity = fileMapper().toEntity(dto);
		T updatedEntity = fileService().update(entity, newEntity);
	}

	@Transactional
	public void updatePosition(Long fileId, UpdateFilePositionRequest dto) {
		T entity = fileAdapter().findById(fileId);
		T beforeEntity = dto.beforeId() == null ? null : fileAdapter().findById(dto.beforeId());
		T afterEntity = dto.afterId() == null ? null : fileAdapter().findById(dto.afterId());

		F folder = folderAdapter().findById(dto.folderId());

		T updatedEntity = fileService().updatePosition(entity, beforeEntity, afterEntity, folder);
	}
}
