package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastUserCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final CastNodeAdaptor castNodeAdaptor;

    public CastInfoResponse execute(CreateCastRequest createCastRequest) {
        CastNode castNode = castNodeMapper.toEntity(createCastRequest);
        CastNode savedCastNode = castNodeDomainService.createCastNode(castNode);

        return castNodeMapper.toInfoResponse(savedCastNode);
    }
}
