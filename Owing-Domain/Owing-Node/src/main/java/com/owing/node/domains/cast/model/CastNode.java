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

    /**
     * 단방향 관계를 추가하는 메서드<p>
     * (A)-->(B)
     * @param relationship
     */
    public void connectCast(CastRelationship relationship) {
        if (!isConnectable(relationship)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS,
                    "Connection Type: %s, Source Id: %d, Target Id: %d"
                            .formatted(ConnectionType.DIRECTIONAL, relationship.getSourceId(), relationship.getTargetId())
            );
        }
        outConnections.add(relationship);
    }

    /**
     * 양방향 관계를 추가하는 메서드<p>
     * (A)<-->(B)
     * @param relationship
     */
    public void biconnectCast(CastRelationship relationship) {
        if (!isBiconnectable(relationship)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS,
                    "Requested Connection Type: %s, Source Id: %d, Target Id: %d"
                            .formatted(ConnectionType.BIDIRECTIONAL, relationship.getSourceId(), relationship.getTargetId()));
        }

        outBiConnections.add(relationship);
    }

    /**
     * (A)<-->(B) 양방향 연결이 가능한지 확인하는 메서드<p>
     * 1. (A)-->(B) 단방향 연결, <p>
     * 2. (A)<--(B) 단방향 연결, <p>
     * 3. (A)<-->(B) 양방향 연결을 확인한다
     * @param relationship
     * @return
     */
    private boolean isBiconnectable(CastRelationship relationship) {
        Set<CastRelationship> inConnections = relationship.getTargetNode().outConnections;
        return isConnectable(relationship)
                && !isDuplicateConnection(this, inConnections);
    }

    /**
     * (A)-->(B) 단방향 연결이 가능한지 확인하는 메서드 <p>
     * 1. (A)-->(B) 단방향 연결, <p>
     * 2. (A)<-->(B) 양방향 연결을 확인한다.
     * @param relationship
     * @return
     */
    private boolean isConnectable(CastRelationship relationship) {
        return !(isDuplicateConnection(relationship.getTargetNode(), outConnections)
                || isDuplicateConnection(relationship.getTargetNode(), outBiConnections)
                || isDuplicateConnection(this, inBiConnections));
    }

    private boolean isDuplicateConnection(CastNode target, Set<CastRelationship> connections) {
        return connections.stream()
                .anyMatch(conn -> conn.getTargetId().equals(target.getId()));
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
