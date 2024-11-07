package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.CastNode;

public record CastInfoProjection(
        Long id,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl
) {
    public static CastInfoProjection from(CastNode castNode) {
        return new CastInfoProjection(
                castNode.getId(),
                castNode.getName(),
                castNode.getAge(),
                castNode.getGender(),
                castNode.getRole(),
                castNode.getDescription(),
                castNode.getImageUrl()
        );
    }
}
