package com.owing.node.folder.cast.model;

import com.owing.core.dnd.file.model.BaseFile;
import com.owing.node.common.model.BaseFolderNode;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeRelationshipException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Node("CastFolder")
@Getter
public class CastFolderNode extends BaseFolderNode {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String description;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private ProjectNode project;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.OUTGOING)
    private List<CastNode> cast;

    // TODO 임시 작성. FileNode 임포트하고 cast들로 변경 필요
    public List<BaseFile> getFiles(){
        return null;
    }

    @Builder
    public CastFolderNode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void connectProject(ProjectNode projectNode) {
        if (!ObjectUtils.isEmpty(this.project)) {
            throw CastFolderNodeRelationshipException.of(
                    CastFolderNodeErrorCode.RELATIONSHIP_ALREADY_EXISTS,
                    "CastFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
                            .formatted(this.id, this.project.getId(), projectNode.getId())
            );
        }

        this.project = projectNode;
    }

    public boolean updateDescription(String description) {
        if (ObjectUtils.isEmpty(description)) {
            return false;
        }
        this.description = description;
        return true;
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
