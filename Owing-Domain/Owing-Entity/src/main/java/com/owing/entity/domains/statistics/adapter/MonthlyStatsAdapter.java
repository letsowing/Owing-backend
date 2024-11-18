package com.owing.entity.domains.statistics.adapter;

import java.time.LocalDate;
import java.time.YearMonth;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.model.MonthlyStats;
import com.owing.entity.domains.statistics.repository.MonthlyStatsRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class MonthlyStatsAdapter {
	private final MonthlyStatsRepository repository;

	public MonthlyStats findById(Long id){
		return repository.findById(id).orElseThrow(() -> new RuntimeException("T.T"));
	}

	public int getCountByYearMonth(Long memberId, YearMonth yearMonth) {
		return repository.getCountByYearMonth(memberId, yearMonth.getYear(), yearMonth.getMonthValue());
	}

	public MonthlyStats getOrCreate(Member member, LocalDate month) {
		return repository.findByMemberAndMonth(member, month).orElseGet(() ->
			MonthlyStats.builder()
				.member(member)
				.month(month)
				.build());
	}

	public MonthlyStats save(MonthlyStats monthlyStats){
        return repository.save(monthlyStats);
    }
}
