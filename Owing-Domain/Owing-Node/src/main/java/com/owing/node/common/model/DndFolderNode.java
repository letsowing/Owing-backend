package com.owing.node.common.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

import com.owing.core.dnd.folder.model.DndFolder;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;
import com.owing.node.folder.story.error.exception.StoryFolderNodeRelationshipException;

import lombok.Getter;

@Getter
public abstract class DndFolderNode<T extends DndFileNode> extends DndFolder {
	@Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
	protected ProjectNode project;

	@Relationship(type = "INCLUDE", direction = Relationship.Direction.OUTGOING)
	protected List<T> files = new ArrayList<>();

	public void connectProject(ProjectNode projectNode) {
		if (!ObjectUtils.isEmpty(this.project)) {
			throw StoryFolderNodeRelationshipException.of(
				StoryFolderNodeErrorCode.RELATIONSHIP_ALREADY_EXISTS,
				"StoryFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
					.formatted(getId(), this.project.getId(), projectNode.getId())
			);
		}

		this.project = projectNode;
	}
}
