package com.owing.api.common.constant;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public enum ProjectSort {
    CREATED_AT("createdAt", Direction.DESC),
    ACCESSED_AT("accessedAt", Direction.DESC);

    final String field;
    final Direction direction;
    ProjectSort(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public Sort getSort() {
        return Sort.by(direction, field)
                .and(Sort.by(Direction.ASC, "projectInfo.title"));
    }
}
