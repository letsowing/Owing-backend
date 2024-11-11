package com.owing.api.cast.service;

import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastUseCase {

    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeDomainService castNodeDomainService;

    public void execute(Long castId) {
        CastNode castNode = castNodeAdapter.findOneById(castId);

        //TODO member의 삭제 권한 판별 추가

        castNodeDomainService.deleteCastNode(castNode);
    }
}
