package com.owing.api.common;

import com.owing.entity.domains.member.adaptor.MemberAdaptor;
import com.owing.entity.domains.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtils {
    private final MemberAdaptor memberAdaptor;

    public Long getCurrentMemberId() {
        // TODO : Context에서 MemberId 가져오기
        return 1L;
    }

    public Member getCurrentMember() {
        return memberAdaptor.findById(getCurrentMemberId());
    }

    public Member getCurrentMemberReference() {
        return memberAdaptor.getReferenceById(getCurrentMemberId());
    }
}
