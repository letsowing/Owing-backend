package com.owing.node.folder.story.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.domains.project.model.ProjectNode;
import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("StoryFolder")
@Getter
public class StoryFolderNode extends BaseTimeNeo4j {

    @Id
    private Long id;

    @Version
    private Long version;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private ProjectNode project;

    public StoryFolderNode(Long id) {
        this.id = id;
    }

    public void connectProject(ProjectNode projectNode) {
        this.project = projectNode;
    }
}
