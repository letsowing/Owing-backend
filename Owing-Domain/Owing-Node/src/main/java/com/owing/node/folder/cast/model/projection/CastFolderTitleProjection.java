package com.owing.node.folder.cast.model.projection;

import com.owing.node.folder.cast.model.CastFolderNode;

public record CastFolderTitleProjection(
        Long id,
        Long version,
        String name
) {
    public static CastFolderTitleProjection from(CastFolderNode castFolderNode) {
        return new CastFolderTitleProjection(castFolderNode.getId(), castFolderNode.getVersion(), castFolderNode.getName());
    }
}
