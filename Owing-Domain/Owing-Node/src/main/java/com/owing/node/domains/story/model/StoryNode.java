package com.owing.node.domains.story.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.common.model.FileNode;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;
import com.owing.node.domains.story.error.exception.StoryNodeRelationshipException;
import com.owing.node.folder.story.model.StoryFolderNode;
import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

@Node("Story")
@Getter
public class StoryNode extends BaseTimeNeo4j implements FileNode<StoryFolderNode> {

    @Id
    private Long id;

    @Version
    private Long version;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private StoryFolderNode storyFolder;

    public StoryNode(Long id) {
        this.id = id;
    }

    @Override
    public void connectFolder(StoryFolderNode storyFolderNode) {
        if (!ObjectUtils.isEmpty(this.storyFolder)) {
            throw StoryNodeRelationshipException.of(
                    StoryNodeErrorCode.RELATED_FOLDER_ALREADY_EXISTS,
                    "StoryFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
                            .formatted(this.id, this.storyFolder.getId(), storyFolderNode.getId())
            );
        }

        this.storyFolder = storyFolderNode;
    }
}
