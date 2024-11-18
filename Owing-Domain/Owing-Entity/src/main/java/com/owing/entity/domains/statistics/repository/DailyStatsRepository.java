package com.owing.entity.domains.statistics.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.entity.domains.statistics.model.DailyStats;

public interface DailyStatsRepository extends JpaRepository<DailyStats, Long> {
	Optional<DailyStats> findByMemberIdAndDate(Long memberId, LocalDate date);

	List<DailyStats> findAllByMemberIdAndDateBetweenOrderByDate(Long memberId, LocalDate start, LocalDate end);
}
