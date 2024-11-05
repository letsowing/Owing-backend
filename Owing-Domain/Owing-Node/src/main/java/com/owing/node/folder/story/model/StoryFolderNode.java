package com.owing.node.folder.story.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("StoryFolder")
@Getter
public class StoryFolderNode extends BaseTimeNeo4j {

    @Id
    private Long id;

    @Version
    private Long version;

    public StoryFolderNode(Long id) {
        this.id = id;
    }
}
