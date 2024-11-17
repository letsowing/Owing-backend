package com.owing.entity.domains.statistics.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.model.MonthlyStats;

public interface MonthlyStatsRepository extends JpaRepository<MonthlyStats, Long> {
	Optional<MonthlyStats> findByMemberAndMonth(Member member, LocalDate month);

	@Query("select coalesce(sum(m.monthlyCount), 0) from MonthlyStats m where m.member = :member and year(m.month) = :year and month(m.month) = :month")
	int getCountByYearMonth(Member member, int year, int month);
}
