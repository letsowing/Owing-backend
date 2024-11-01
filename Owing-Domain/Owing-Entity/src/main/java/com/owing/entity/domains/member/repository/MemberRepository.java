package com.owing.entity.domains.member.repository;

import com.owing.entity.domains.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
