package com.owing.entity.domains.member.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberRepository;

    @Transactional("jpaTransactionManager")
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
