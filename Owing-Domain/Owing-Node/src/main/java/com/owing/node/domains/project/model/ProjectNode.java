package com.owing.node.domains.project.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.folder.story.model.StoryFolderNode;
import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Project")
@Getter
public class ProjectNode extends BaseTimeNeo4j {

    @Id
    private Long id;

    @Version
    private Long version;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.OUTGOING)
    private Set<StoryFolderNode> storyFolderNodes = new HashSet<>();

    public ProjectNode(Long id) {
        this.id = id;
    }

    public void connectStoryFolder(StoryFolderNode storyFolderNode) {
        this.storyFolderNodes.add(storyFolderNode);
    }

}
