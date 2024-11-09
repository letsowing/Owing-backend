package com.owing.core.dnd.file.model;

import com.owing.core.constant.OwingPersistenceConst;
import org.springframework.util.StringUtils;

import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndException;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseFileEntity<T extends BaseFolderEntity> implements BaseFile<T>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	protected Long position;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String title;

	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	protected T folder;


	public void updatePosition(long newPosition) {
		if(!validatePosition(newPosition)){
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}

	public void updateTitle(String newTitle) {
		if (!StringUtils.hasText(newTitle)) {
            throw DndException.of(DndErrorCode.INVALID_TITLE);
        }
        this.title = newTitle;
	}

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
