package com.owing.api.cast.model.dto.response;

import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;

import java.util.List;

public record CastGraphResponse(
        List<CastGraphNodeProjection> cast,
        List<CastGraphRelationshipProjection> relationship
) {
}
