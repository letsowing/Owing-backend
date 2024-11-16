package com.owing.entity.domains.statistics.service;

import java.time.YearMonth;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.adapter.MonthlyStatsAdapter;
import com.owing.entity.domains.statistics.model.MonthlyStats;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MonthlyStatsService {
	private final MonthlyStatsAdapter adapter;

	public int getMonthlyCount(Member member, YearMonth yearMonth) {
		return adapter.getCountByYearMonth(member, yearMonth);
	}

	public void updateMonthlyCount(Member member, int monthlyCount){
		MonthlyStats entity = adapter.getOrCreate(member);
		entity.updateMonthlyCount(monthlyCount);
        adapter.save(entity);
	}
}
