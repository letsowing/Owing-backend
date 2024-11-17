package com.owing.node.folder.cast.model.projection;

import com.owing.node.folder.cast.model.CastFolderNode;

public record CastFolderPositionProjection(
        Long id,
        Long version,
        Long position
) {
    public static CastFolderPositionProjection from(CastFolderNode castFolderNode) {
        return new CastFolderPositionProjection(castFolderNode.getId(), castFolderNode.getVersion(), castFolderNode.getPosition());
    }
}
