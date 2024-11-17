package com.owing.core.dnd.file.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndException;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFileEntity<T extends BaseFolderEntity> implements BaseFile<T>{

	@Column(nullable = false)
	protected Long position;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	protected T folder;

	@CreatedDate
	private LocalDateTime createdAt; //fixme
	@LastModifiedDate
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public void updatePosition(long newPosition) {
		if(!validatePosition(newPosition)){
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}

	public void updateName(String newName) {
		if (!StringUtils.hasText(newName)) {
            throw DndException.of(DndErrorCode.INVALID_TITLE);
        }
        this.name = newName;
	}

	public void update(BaseFileEntity<T> newFile){
		this.name = newFile.getName();
		this.description = newFile.getDescription();
	}

	public void updateFolder(T folder){
		this.folder = folder;
	}

	@Override
	public Long getParentId(){
		return folder.getId();
	}

	@Override
	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

}
