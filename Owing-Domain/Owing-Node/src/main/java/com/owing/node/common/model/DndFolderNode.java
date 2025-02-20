package com.owing.node.common.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.owing.core.BaseEntity;
import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndException;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFolder;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;
import com.owing.node.folder.story.error.exception.StoryFolderNodeRelationshipException;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public abstract class DndFolderNode<T extends DndFileNode> extends BaseEntity implements DndFolder {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@Column(name = "project_id")
	protected Long projectId;

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

	public Long getParentId() {
		return this.projectId;
	}

	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	public void updateName(String newTitle) {
		if (!StringUtils.hasText(newTitle)) {
			throw DndException.of(DndErrorCode.INVALID_TITLE);
		}
		this.name = newTitle;
	}

	public void updatePosition(long newPosition) {
		if (!validatePosition(newPosition)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}

	public boolean updateDescription(String description) {
		if (ObjectUtils.isEmpty(description)) {
			return false;
		}
		this.description = description;
		return true;
	}
	public boolean isInSameParent(Dnd dnd) {
		return this.getProjectId().equals(dnd.getParentId());
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
}
