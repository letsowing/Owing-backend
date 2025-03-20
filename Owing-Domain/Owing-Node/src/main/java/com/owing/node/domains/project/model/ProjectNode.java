package com.owing.node.domains.project.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import com.owing.core.BaseEntity;

import lombok.Getter;

@Node("Project")
@Getter
public class ProjectNode extends BaseEntity {

    @Id
    private Long id;

    @Version
    private Long version;

//    @Relationship(type = "INCLUDE", direction = Relationship.Direction.OUTGOING)
//    private Set<StoryFolderNode> storyFolderNodes = new HashSet<>();

    public ProjectNode(Long id) {
        this.id = id;
    }

//    public void connectStoryFolder(StoryFolderNode storyFolderNode) {
//        this.storyFolderNodes.add(storyFolderNode);
//    }

}
