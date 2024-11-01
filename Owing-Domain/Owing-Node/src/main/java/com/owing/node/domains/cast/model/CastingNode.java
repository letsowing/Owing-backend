package com.owing.node.domains.cast.model;

import com.owing.node.common.model.BaseTimeNeo4j;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node("Cast")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CastingNode extends BaseTimeNeo4j {
    @Id
    private Long id;
    private String name;
    private Long age;
    private String gender;
    private String role;
    private String imageUrl;
    private Coord coord;

    @Relationship(type = "CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastingRelationship> outConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastingRelationship> outBiConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.INCOMING)
    private Set<CastingRelationship> inBiConnections;

//    @Relationship(type = "APPEARED",  direction = Relationship.Direction.OUTGOING)
//    private Set<StoryPlotNode> episodes;
//
//    @Relationship(type = "INCLUDED", direction = Relationship.Direction.INCOMING)
//    private ProjectNode projectNode;

}
