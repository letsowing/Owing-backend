package com.owing.entity.domains.member.repository;

import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmailAndProvider(String email, OauthProvider provider);
}
