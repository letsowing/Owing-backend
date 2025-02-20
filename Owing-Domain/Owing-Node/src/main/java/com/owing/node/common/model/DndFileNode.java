package com.owing.node.common.model;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.owing.core.BaseEntity;
import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndException;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;
import com.owing.node.domains.story.error.exception.StoryNodeRelationshipException;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public abstract class DndFileNode<T extends DndFolderNode> extends BaseEntity implements DndFile {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
	protected T folder;

	public void updateFolder(DndFolder folder){
		this.folder = (T)folder;
	}

	public void connectFolder(DndFolder folder) {
		if (!ObjectUtils.isEmpty(this.folder)) {
			throw StoryNodeRelationshipException.of(
				StoryNodeErrorCode.RELATED_FOLDER_ALREADY_EXISTS,
				"StoryFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
					.formatted(getId(), this.folder.getId(), folder.getId())
			);
		}

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
