package com.owing.node.folder.cast.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.common.model.FolderNode;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeRelationshipException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

@Node("CastFolder")
@Getter
public class CastFolderNode extends BaseTimeNeo4j implements FolderNode {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String name;
    private String description;
    private Long position;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private ProjectNode project;

    @Override
    public void connectProject(ProjectNode projectNode) {
        if (ObjectUtils.isEmpty(this.project)) {
            throw CastFolderNodeRelationshipException.of(
                    CastFolderNodeErrorCode.RELATIONSHIP_ALREADY_EXISTS,
                    "CastFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
                            .formatted(this.id, this.project.getId(), projectNode.getId())
            );
        }

        this.project = projectNode;
    }
}
