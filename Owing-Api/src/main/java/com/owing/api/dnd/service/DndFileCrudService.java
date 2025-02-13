package com.owing.api.dnd.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.model.dto.request.AddFileRequest;
import com.owing.api.dnd.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DndFileCrudService<T extends DndFile, F extends DndFolder> implements DndCrudService<AddFileRequest, UpdateFileTitleRequest, UpdateFilePositionRequest> {
	protected abstract MemberUtils memberUtils();
	protected abstract DndService<T> orderService();
	protected abstract DndAdapter<T> fileAdapter();
	protected abstract BaseFileMapper<T, F> fileMapper();
	protected abstract DndAdapter<F> folderAdapter();
	protected abstract TrashCanDomainService trashCanDomainService();

	public DndInfoResponse get(Long fileId){
		T entity = fileAdapter().findById(fileId);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional("jpaTransactionManager")
	public DndInfoResponse create(AddFileRequest dto) {
		F folder = folderAdapter().findById(dto.folderId());
		T entity = fileMapper().toEntity(dto, folder);
		entity = orderService().create(entity);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional("jpaTransactionManager")
	public void updateName(Long fileId, UpdateFileTitleRequest dto) {
		T entity = fileAdapter().findById(fileId);
		entity.updateName(dto.name());
		fileAdapter().updateName(entity);
	}

	@Transactional("jpaTransactionManager")
	public void updatePosition(Long fileId, UpdateFilePositionRequest dto) {
		T entity = fileAdapter().findById(fileId);
		T beforeEntity = dto.beforeId() == null ? null : fileAdapter().findById(dto.beforeId());
		T afterEntity = dto.afterId() == null ? null : fileAdapter().findById(dto.afterId());

		F folder = folderAdapter().findById(dto.folderId());

		T updatedEntity = orderService().updatePosition(entity, beforeEntity, afterEntity, folder);
	}

	@Transactional("jpaTransactionManager")
	public void delete(Long fileId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		T entity = fileAdapter().findById(fileId);
		trashCanDomainService().trash(entity);
		orderService().delete(entity);
	}
}
