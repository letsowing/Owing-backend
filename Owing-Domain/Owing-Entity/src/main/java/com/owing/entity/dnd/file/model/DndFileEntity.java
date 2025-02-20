package com.owing.entity.dnd.file.model;

import org.springframework.util.StringUtils;

import com.owing.core.BaseEntity;
import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndException;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.entity.dnd.folder.model.DndFolderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class DndFileEntity<T extends DndFolderEntity> extends BaseEntity implements DndFile {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	protected T folder;

	public void updateFolder(DndFolder folder){
		this.folder = (T)folder;
	}

	public void updateName(String newName) {
		if (!StringUtils.hasText(newName)) {
			throw DndException.of(DndErrorCode.INVALID_TITLE);
		}
		this.name = newName;
	}

	public void updatePosition(long newPosition) {
		if(!validatePosition(newPosition)){
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}


	public void update(DndFile newFile){
		this.name = newFile.getName();
		this.description = newFile.getDescription();
	}

	public Long getParentId(){
		return getFolder().getId();
	}

	public boolean isInSameParent(Dnd file){
		return this.getParentId().equals(file.getParentId());
	}

	public boolean isChildOf(DndFolder folder){
		return this.getParentId().equals(folder.getId());
	}

	public boolean isAfter(Dnd dnd){
		return this.getPosition() > dnd.getPosition();
	}

	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}
}
