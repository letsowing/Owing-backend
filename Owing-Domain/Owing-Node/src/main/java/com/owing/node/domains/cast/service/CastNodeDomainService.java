package com.owing.node.domains.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastNodeDomainService {

    private final CastNodeRepository castNodeRepository;
    private final CastNodeAdaptor castNodeAdaptor;

    @Transactional
    public CastNode createCastNode(CastNode castNode, CastFolderNode castFolderNode) {
        // TODO position 기본값으로 변경
        castNode.updatePosition(0L);
        castNode.updateCoordinate(
                CastConstant.DEFAULT_COORDINATE_X,
                CastConstant.DEFAULT_COORDINATE_Y
        );
        castNode.connectFolder(castFolderNode);
        return castNodeRepository.save(castNode);
    }
}
