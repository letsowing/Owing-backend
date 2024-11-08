package com.owing.entity.domains.member.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
