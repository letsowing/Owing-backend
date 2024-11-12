package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;

public record CastPositionProjection(
        Long id,
        Long position
) {
    public static CastPositionProjection from(CastNode castNode) {
        return new CastPositionProjection(castNode.getId(), castNode.getPosition());
    }
}
