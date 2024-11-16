package com.owing.node.domains.cast.model.dto;

import com.owing.node.domains.cast.model.Coordinate;

public record CastInfo(
        Long id,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl,
        Coordinate coordinate
){}
