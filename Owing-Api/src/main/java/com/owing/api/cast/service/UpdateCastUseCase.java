package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastUseCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeMapper castNodeMapper;

    public void executeUpdateInfo(Long castId, UpdateCastInfoRequest updateCastInfoRequest) {
        CastNode castNode = castNodeAdapter.findOneById(castId);

        CastNodeInfo castNodeInfo = castNodeMapper.toCastNodeInfo(updateCastInfoRequest);
        castNodeDomainService.updateCastNodeInfo(castNode, castNodeInfo);
    }


    public void executeUpdateCoordinate(Long castId, UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        CastNode castNode = castNodeAdapter.findOneById(castId);

        Coordinate coordinate = castNodeMapper.toCoordinate(updateCastCoordinateRequest);
        castNodeDomainService.updateCastNodeCoordinate(castNode, coordinate);
    }
}
