package com.owing.node.domains.cast.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeNotFoundException;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CastNodeAdaptor {

    private final CastNodeRepository castNodeRepository;

    public CastNode findById(Long castId) {
        return castNodeRepository.findById(castId)
                .orElseThrow(() -> CastNodeNotFoundException.of(
                        CastNodeErrorCode.CAST_NODE_NOT_FOUND,
                        "Cast Node Id: %d".formatted(castId)
                ));
    }
}
