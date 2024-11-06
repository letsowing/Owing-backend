package com.owing.node.domains.cast.model;

import com.owing.node.common.converter.CoordinateConverter;
import com.owing.node.common.model.BaseTimeNeo4j;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Node("Cast")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CastNode extends BaseTimeNeo4j {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long age;
    private String gender;
    private String role;
    private String description;
    private String imageUrl;

    @CompositeProperty(converter = CoordinateConverter.class)
    private Coordinate coordinate;
    private Long position;

    @Builder
    public CastNode(String name, Long age, String gender, String role, String description, String imageUrl, Coordinate coordinate, Long position) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.description = description;
        this.imageUrl = imageUrl;
        this.coordinate = coordinate;
        this.position = position;
    }

    @Relationship(type = "CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastRelationship> outConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastRelationship> outBiConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.INCOMING)
    private Set<CastRelationship> inBiConnections;

//    @Relationship(type = "APPEARED",  direction = Relationship.Direction.OUTGOING)
//    private Set<StoryPlotNode> episodes;
//
//    @Relationship(type = "INCLUDED", direction = Relationship.Direction.INCOMING)
//    private ProjectNode projectNode;

    public void updatePosition(Long position) {
        this.position = position;
    }

    public void updateCoordinate(Integer x, Integer y) {
        this.updateCoordinate(new Coordinate(x, y));
    }

    public void updateCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
