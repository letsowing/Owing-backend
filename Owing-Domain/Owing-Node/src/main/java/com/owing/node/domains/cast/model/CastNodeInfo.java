package com.owing.node.domains.cast.model;

public record CastNodeInfo(
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl
) {
}
