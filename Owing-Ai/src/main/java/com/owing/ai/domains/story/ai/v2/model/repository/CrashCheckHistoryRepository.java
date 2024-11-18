package com.owing.ai.domains.story.ai.v2.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.ai.domains.story.ai.v2.model.entity.CrashCheckHistory;

public interface CrashCheckHistoryRepository extends JpaRepository<CrashCheckHistory, Long> {
}
