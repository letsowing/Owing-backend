package com.owing.entity.dnd.folder.model;

import java.util.ArrayList;
import java.util.List;

import com.owing.core.dnd.folder.model.DndFolder;
import com.owing.entity.dnd.file.model.DndFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class DndFolderEntity<T extends DndFileEntity> extends DndFolder {

	@OrderBy("position ASC")
	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<T> files = new ArrayList<>();

}
