package com.owing.node.folder.story.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import com.owing.node.common.model.DndFolderNode;

import lombok.Getter;

@Node("StoryFolder")
@Getter
public class StoryFolderNode extends DndFolderNode {
    @Id
    private Long id;

    @Version
    private Long version;

    public StoryFolderNode(Long id) {
        this.id = id;
    }
}
