package com.owing.node.domains.story.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import com.owing.node.common.model.DndFileNode;
import com.owing.node.folder.story.model.StoryFolderNode;

import lombok.Getter;

@Node("Story")
@Getter
public class StoryNode extends DndFileNode<StoryFolderNode> {
    @Id
    private Long id;

    @Version
    private Long version;

    public StoryNode(Long id) {
        this.id = id;
    }

}
