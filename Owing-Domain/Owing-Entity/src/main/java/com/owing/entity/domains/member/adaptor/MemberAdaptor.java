package com.owing.entity.domains.member.adaptor;

import com.owing.entity.domains.member.error.MemberErrorCode;
import com.owing.entity.domains.member.error.exception.MemberException;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> MemberException.of(MemberErrorCode.MEMBER_NOT_FOUND, "요청된 Member Id: %d".formatted(memberId)));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> MemberException.of(MemberErrorCode.MEMBER_NOT_FOUND, "요청된 Member email: %s".formatted(email)));
    }

    public Member findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByEmail(phoneNumber)
                .orElseThrow(() -> MemberException.of(MemberErrorCode.MEMBER_NOT_FOUND, "요청된 Member phone number: %s".formatted(phoneNumber)));
    }
}
