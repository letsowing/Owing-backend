package com.owing.core.dnd.base.model;

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
public abstract class DndFile extends BaseEntity implements Dnd {

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	public abstract <T extends DndFolder> T getFolder();

	public Long getParentId(){
		return getFolder().getId();
	}

	public boolean isParentFolder(Dnd folder){
		return this.getParentId().equals(folder.getId());
	}

	public boolean isInSameParent(DndFile file){
		return this.getParentId().equals(file.getParentId());
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

	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
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

	public abstract void updateFolder(DndFolder folder);
}
