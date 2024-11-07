package com.owing.node.folder.cast.model.projection;

import com.owing.node.folder.cast.model.CastFolderNode;

public record CastFolderInfoProjection(
        Long id,
        Long version,
        String name,
        String description
) {
    public static CastFolderInfoProjection from(CastFolderNode castFolderNode) {
        return new CastFolderInfoProjection(castFolderNode.getId(), castFolderNode.getVersion(), castFolderNode.getName(), castFolderNode.getDescription());
    }
}