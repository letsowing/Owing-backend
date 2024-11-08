package com.owing.node.folder.cast.model.projection;

import com.owing.node.folder.cast.model.CastFolderNode;

public record CastFolderDeleteProjection(
        Long id,
        Long version,
        Boolean deleted
) {
    public static CastFolderDeleteProjection from(CastFolderNode castFolderNode) {
        return new CastFolderDeleteProjection(castFolderNode.getId(), castFolderNode.getVersion(), castFolderNode.getDeleted());
    }
}
