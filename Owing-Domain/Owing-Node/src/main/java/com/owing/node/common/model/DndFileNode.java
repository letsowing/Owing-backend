package com.owing.node.common.model;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;
import com.owing.node.domains.story.error.exception.StoryNodeRelationshipException;

import lombok.Getter;

@Getter
public abstract class DndFileNode<T extends DndFolderNode> extends DndFile {
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
}
