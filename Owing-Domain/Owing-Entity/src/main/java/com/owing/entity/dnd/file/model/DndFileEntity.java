package com.owing.entity.dnd.file.model;

import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.entity.dnd.folder.model.DndFolderEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class DndFileEntity<T extends DndFolderEntity> extends DndFile {
	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	protected T folder;

	public void updateFolder(DndFolder folder){
		this.folder = (T)folder;
	}
}
