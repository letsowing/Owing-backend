package com.owing.entity.dnd.folder.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.owing.core.BaseEntity;
import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndException;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFolder;
import com.owing.entity.dnd.file.model.DndFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class DndFolderEntity<T extends DndFileEntity> extends BaseEntity implements DndFolder {

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@Column(name = "project_id")
	protected Long projectId;

	@OrderBy("position ASC")
	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<T> files = new ArrayList<>();


	public Long getParentId() {
		return this.projectId;
	}

	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	public void updateName(String newTitle) {
		if (!StringUtils.hasText(newTitle)) {
			throw DndException.of(DndErrorCode.INVALID_TITLE);
		}
		this.name = newTitle;
	}

	public void updatePosition(long newPosition) {
		if (!validatePosition(newPosition)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}

	public boolean updateDescription(String description) {
		if (ObjectUtils.isEmpty(description)) {
			return false;
		}
		this.description = description;
		return true;
	}
	public boolean isInSameParent(Dnd dnd) {
		return this.getProjectId().equals(dnd.getParentId());
	}

	public boolean isAfter(Dnd dnd){
		return this.getPosition() > dnd.getPosition();
	}

	public boolean isAfter(long position){
		return this.getPosition() > position;
	}

	public boolean isBefore(Dnd dnd){
		return this.getPosition() < dnd.getPosition();
	}

	public boolean isBefore(long position){
		return this.getPosition() < position;
	}
}
