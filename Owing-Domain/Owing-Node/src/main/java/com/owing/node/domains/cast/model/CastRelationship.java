package com.owing.node.domains.cast.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

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

}
