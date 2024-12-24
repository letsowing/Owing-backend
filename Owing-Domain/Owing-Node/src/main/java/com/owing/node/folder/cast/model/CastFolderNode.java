package com.owing.node.folder.cast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import com.owing.node.common.model.DndFolderNode;
import com.owing.node.domains.cast.model.CastNode;

import lombok.Builder;
import lombok.Getter;


@Node("CastFolder")
@Getter
public class CastFolderNode extends DndFolderNode<CastNode> {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;


    @Builder
    public CastFolderNode(String name) {
        this.name = name;
    }


    @Override
    public Long getProjectId() {
        return this.getParentId();
    }

    @Override
    public Long getParentId() {
        return this.project.getId();
    }
}
