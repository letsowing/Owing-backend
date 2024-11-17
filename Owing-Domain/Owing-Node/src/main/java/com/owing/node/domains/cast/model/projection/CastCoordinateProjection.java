package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.Coordinate;

public record CastCoordinateProjection(
        Long id,
        Coordinate coordinate
) {
    public static CastCoordinateProjection from(CastNode castNode) {
        return new CastCoordinateProjection(
                castNode.getId(),
                new Coordinate(castNode.getCoordinate().x(), castNode.getCoordinate().y())
        );
    }
}
