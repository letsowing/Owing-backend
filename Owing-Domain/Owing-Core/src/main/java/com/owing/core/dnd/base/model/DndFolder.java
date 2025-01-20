package com.owing.core.dnd.base.model;

import java.util.List;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.owing.core.BaseEntity;
import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndException;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class DndFolder extends BaseEntity implements Dnd {

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@Column(name = "project_id")
	protected Long projectId;


	public Long getParentId() {
		return this.projectId;
	}

	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	public abstract List<? extends DndFile> getFiles();

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
