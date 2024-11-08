package com.owing.api.project.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.node.domains.project.model.ProjectNode;

@Mapper
public class ProjectNodeMapper {

    public ProjectNode toNode(Long projectId) {
        return new ProjectNode(projectId);
    }
}
