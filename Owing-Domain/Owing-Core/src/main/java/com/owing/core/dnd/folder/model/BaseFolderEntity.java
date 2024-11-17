package com.owing.core.dnd.folder.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndException;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.file.model.BaseFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFolderEntity<T extends BaseFileEntity> implements BaseFolder {

	@Column(nullable = false)
	protected Long position;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(name = "project_id")
	protected Long projectId;

	@OrderBy("position ASC")
	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<T> files = new ArrayList<>();

	@CreatedDate
	private LocalDateTime createdAt; //fixme
	@LastModifiedDate
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public Long getParentId() {
		return this.projectId;
	}

	@Override
	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	public void updateTitle(String newTitle) {
		if (!StringUtils.hasText(newTitle)) {
			throw DndException.of(DndErrorCode.INVALID_TITLE);
		}
		this.name = newTitle;
	}

	@Override
	public void updatePosition(long newPosition) {
		if (!validatePosition(newPosition)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}
}
