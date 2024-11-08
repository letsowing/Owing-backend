package com.owing.entity.dnd.file.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.dnd.base.model.DndEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public abstract class BaseFileEntity<T extends BaseFolderEntity> extends DndEntity {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String title;

	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	protected T folder;

	public void update(BaseFileEntity<T> newFile){
		this.title = newFile.getTitle();
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
