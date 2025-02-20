package com.owing.api.dnd.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.dto.request.AddFileRequest;
import com.owing.api.dnd.dto.request.UpdateFileNameRequest;
import com.owing.api.dnd.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.dto.response.FileInfoResponse;
import com.owing.api.dnd.mapper.DndFileMapper;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

public abstract class DndFileCrudService<T extends DndFile, F extends DndFolder> {
	protected abstract DndService<T> dndService();
	protected abstract DndAdapter<T> fileAdapter();
	protected abstract DndAdapter<F> folderAdapter();
	protected abstract DndFileMapper<T, F> fileMapper();
	protected abstract TrashCanDomainService trashCanDomainService();

	public FileInfoResponse get(Long fileId){
		T entity = fileAdapter().findById(fileId);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional("jpaTransactionManager")
	public FileInfoResponse create(AddFileRequest dto) {
		F folder = folderAdapter().findById(dto.folderId());
		T entity = fileMapper().toEntity(dto, folder);
		entity = dndService().create(entity);
		return fileMapper().toInfoResponse(entity);
	}

	@Transactional("jpaTransactionManager")
	public void updateName(Long fileId, UpdateFileNameRequest dto) {
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

		T updatedEntity = dndService().updatePosition(entity, beforeEntity, afterEntity, folder);
	}

	@Transactional("jpaTransactionManager")
	public void delete(Long fileId) {
		// Long memberId = memberUtils.getCurrentMemberId();
		T entity = fileAdapter().findById(fileId);
		dndService().delete(entity);
		trashCanDomainService().trash(entity);
	}
}
