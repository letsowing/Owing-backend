package com.owing.node.domains.cast.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeNotFoundException;
import com.owing.node.domains.cast.error.exception.CastRelationshipNotFoundException;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CastNodeAdaptor {

    private final CastNodeRepository castNodeRepository;

    @Deprecated
    public CastNode findById(Long castId) {
        return castNodeRepository.findById(castId)
                .orElseThrow(() -> CastNodeNotFoundException.of(
                        CastNodeErrorCode.CAST_NODE_NOT_FOUND,
                        "Requested Cast Node Id: %d".formatted(castId)
                ));
    }

    public CastNode findOneById(Long castId) {
        return castNodeRepository.findOneById(castId)
                .orElseThrow(() -> CastNodeNotFoundException.of(
                        CastNodeErrorCode.CAST_NODE_NOT_FOUND,
                        "Requested Cast Node Id: %d".formatted(castId)
                ));
    }

    public CastRelationshipProjection findConnection(Long sourceId, Long targetId) {
        return castNodeRepository.findConnection(sourceId, targetId)
                .orElseThrow(() -> CastRelationshipNotFoundException.of(
                        CastNodeErrorCode.RELATIONSHIP_NOT_FOUND,
                        "Source Id: %d, Target Id: %d".formatted(sourceId, targetId)
                ));
    }

    public CastRelationshipProjection findBiconnection(Long sourceId, Long targetId) {
        return castNodeRepository.findBiconnection(sourceId, targetId)
                .orElseThrow(() -> CastRelationshipNotFoundException.of(
                        CastNodeErrorCode.RELATIONSHIP_NOT_FOUND,
                        "Source Id: %d, Target Id: %d".formatted(sourceId, targetId)
                ));
    }
}
