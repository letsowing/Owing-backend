package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;

public record CastTitleProjection(
        Long id,
        String name
) {
    public static CastTitleProjection from(CastNode castNode) {
        return new CastTitleProjection(castNode.getId(), castNode.getName());
    }
}
