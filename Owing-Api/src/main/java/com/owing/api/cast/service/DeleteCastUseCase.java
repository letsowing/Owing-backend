package com.owing.api.cast.service;

import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastUseCase {

    private final CastNodeAdaptor castNodeAdaptor;
    private final CastNodeDomainService castNodeDomainService;

    public void execute(Long castId) {
        CastNode castNode = castNodeAdaptor.findOneById(castId);

        //TODO member의 삭제 권한 판별 추가

        castNodeDomainService.deleteCastNode(castNode);
    }
}
