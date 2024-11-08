package com.owing.api.common.util;

import com.owing.api.auth.error.AuthErrorCode;
import com.owing.api.auth.error.exception.AuthException;
import com.owing.entity.domains.member.adaptor.MemberAdaptor;
import com.owing.entity.domains.member.model.Member;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtils {
    private final MemberAdaptor memberAdaptor;

    public Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw AuthException.of(AuthErrorCode.NOT_TOKEN_USERID);
        }

        return (Long) authentication.getPrincipal();
    }

    public Member getCurrentMember() {
        return memberAdaptor.findById(getCurrentMemberId());
    }

    public Member getCurrentMemberReference() {
        return memberAdaptor.getReferenceById(getCurrentMemberId());
    }
}
