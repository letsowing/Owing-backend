package com.owing.node.common.model;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public abstract class BaseFileNode<T extends BaseFolderEntity> extends BaseTimeNeo4j implements BaseFile<T>{
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

	public void update(BaseFileNode<T> newFile){
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
