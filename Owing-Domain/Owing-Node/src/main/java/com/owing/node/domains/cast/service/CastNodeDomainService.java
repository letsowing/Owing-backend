package com.owing.node.domains.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastNodeDomainService {

    private final CastNodeRepository castNodeRepository;
    private final CastNodeAdaptor castNodeAdaptor;

    @Transactional
    public CastNode createCastNode(CastNode castNode) {
        return castNodeRepository.save(castNode);
    }

}
