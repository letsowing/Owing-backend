package com.owing.api.cast.model.dto.request;

import com.owing.node.domains.cast.model.ConnectionHandle;

public record UpdateCastRelationshipHandleRequest(
        ConnectionHandle sourceHandle,
        ConnectionHandle targetHandle
) {
}
