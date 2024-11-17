package com.owing.node.domains.cast.model;

import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeRelationshipException;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.util.ObjectUtils;

@RelationshipProperties
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CastRelationship {

    @RelationshipId
    private Long id;

    private String label;
    private Long sourceId;
    private Long targetId;
    private ConnectionHandle sourceHandle;
    private ConnectionHandle targetHandle;

    @TargetNode
    private CastNode targetNode;

    public void updateLabel(String label) {
        if (ObjectUtils.isEmpty(label)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS);
        }
        this.label = label;
    }

    public void updateSourceId(Long sourceId) {
        if (ObjectUtils.isEmpty(sourceId)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS);
        }
        this.sourceId = sourceId;
    }

    public void updateTargetId(Long targetId) {
        if (ObjectUtils.isEmpty(targetId)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS);
        }
        this.targetId = targetId;
    }

    public void updateSourceHandle(ConnectionHandle sourceHandle) {
        if (ObjectUtils.isEmpty(sourceHandle)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS);
        }
        this.sourceHandle = sourceHandle;
    }

    public void updateTargetHandle(ConnectionHandle targetHandle) {
        if (ObjectUtils.isEmpty(targetHandle)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS);
        }
        this.targetHandle = targetHandle;
    }

    public void update(CastRelationship newRelationship) {
        updateLabel(newRelationship.getLabel());
        updateSourceId(newRelationship.getSourceId());
        updateSourceHandle(newRelationship.getSourceHandle());
        updateTargetId(newRelationship.getTargetId());
        updateTargetHandle(newRelationship.getTargetHandle());
    }

}
