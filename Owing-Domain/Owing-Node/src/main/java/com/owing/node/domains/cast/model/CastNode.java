package com.owing.node.domains.cast.model;

import com.owing.node.common.converter.CoordinateConverter;
import com.owing.node.common.model.BaseTimeNeo4j;
import com.owing.node.common.model.FileNode;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeRelationshipException;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Node("Cast")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CastNode extends BaseTimeNeo4j implements FileNode<CastFolderNode> {
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

    @Relationship(type = "CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastRelationship> outConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<CastRelationship> outBiConnections;

    @Relationship(type = "BI_CONNECTION", direction = Relationship.Direction.INCOMING)
    private Set<CastRelationship> inBiConnections;

//    @Relationship(type = "APPEARED",  direction = Relationship.Direction.OUTGOING)
//    private Set<StoryPlotNode> episodes;

    @Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
    private CastFolderNode castFolder;

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

    @Override
    public void connectFolder(CastFolderNode castFolderNode) {
        if (!ObjectUtils.isEmpty(this.castFolder)) {
            throw CastNodeRelationshipException.of(
                    CastNodeErrorCode.RELATED_FOLDER_ALREADY_EXISTS,
                    "castFolder Id: %d, Connected Project Id: %d, Requested Folder Id: %d"
                            .formatted(this.id, this.castFolder.getId(), castFolderNode.getId())
            );
        }

        this.castFolder = castFolderNode;
    }

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
