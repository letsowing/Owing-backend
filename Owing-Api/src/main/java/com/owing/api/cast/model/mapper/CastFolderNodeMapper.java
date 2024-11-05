package com.owing.api.cast.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.node.folder.cast.model.CastFolderNode;

@Mapper
public class CastFolderNodeMapper {

    public CastFolderNode toEntity(String name, String description) {
        return new CastFolderNode(name, description);
    }
}
