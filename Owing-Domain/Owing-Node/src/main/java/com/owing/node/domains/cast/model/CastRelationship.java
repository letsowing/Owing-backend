package com.owing.node.domains.cast.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
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

    @Builder
    public CastRelationship(String label, CastNode targetNode, Long sourceId, ConnectionHandle sourceHandle, Long targetId, ConnectionHandle targetHandle) {
        this.label = label;
        this.targetNode = targetNode;

        this.sourceId = sourceId;
        this.sourceHandle = sourceHandle;

        this.targetId = targetId;
        this.targetHandle = targetHandle;
    }

//    public void updateLabel(String label) {
//        if (!StringUtils.hasText(label)) {
//            throw CastingException.of(CastingErrorCode.INVALID_ARGS_FOR_UPDATE);
//        }
//        this.label = label;
//    }
}
