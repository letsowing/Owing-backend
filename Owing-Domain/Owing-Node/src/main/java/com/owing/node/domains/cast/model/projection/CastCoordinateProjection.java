package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;

public record CastCoordinateProjection(
        Long id,
        Integer x,
        Integer y
) {
    public static CastCoordinateProjection from(CastNode castNode) {
        return new CastCoordinateProjection(
                castNode.getId(),
                castNode.getCoordinate().x(),
                castNode.getCoordinate().y()
        );
    }
}
