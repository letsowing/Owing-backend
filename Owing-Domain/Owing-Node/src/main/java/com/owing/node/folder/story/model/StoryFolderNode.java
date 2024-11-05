package com.owing.node.folder.story.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.common.model.FolderNode;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.story.model.StoryNode;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;
import com.owing.node.folder.story.error.exception.StoryFolderNodeRelationshipException;
import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Node("StoryFolder")
@Getter
public class StoryFolderNode extends BaseTimeNeo4j implements FolderNode {

    @Id
    private Long id;

    @Version
    private Long version;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private ProjectNode project;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.OUTGOING)
    private Set<StoryNode> stories;

    public StoryFolderNode(Long id) {
        this.id = id;
    }

    @Override
    public void connectProject(ProjectNode projectNode) {
        if (ObjectUtils.isEmpty(this.project)) {
            throw StoryFolderNodeRelationshipException.of(
                    StoryFolderNodeErrorCode.RELATIONSHIP_ALREADY_EXISTS,
                    "StoryFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
                            .formatted(this.id, this.project.getId(), projectNode.getId())
            );
        }

        this.project = projectNode;
    }
}
