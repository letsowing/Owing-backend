package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;

public record CastDeleteProjection(
        Long id,
        Boolean deleted
) {
    public static CastDeleteProjection from(CastNode castFolderNode) {
        return new CastDeleteProjection(castFolderNode.getId(), castFolderNode.getDeleted());
    }
}