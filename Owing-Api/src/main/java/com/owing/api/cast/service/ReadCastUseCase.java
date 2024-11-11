package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadCastUseCase {

    private final CastNodeAdaptor castNodeAdaptor;
    private final CastNodeMapper castNodeMapper;

    public CastInfoResponse execute(Long castId) {
        CastNode castnode = castNodeAdaptor.findOneById(castId);
        return castNodeMapper.toInfoResponse(castnode);
    }
}
