package com.owing.entity.domains.statistics.service;

import java.time.YearMonth;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.adapter.MemberAdapter;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.adapter.MonthlyStatsAdapter;
import com.owing.entity.domains.statistics.model.MonthlyStats;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MonthlyStatsService {
	private final MonthlyStatsAdapter adapter;
	private final MemberAdapter memberAdapter;

	public int getMonthlyCount(Long memberId, YearMonth yearMonth) {
		return adapter.getCountByYearMonth(memberId, yearMonth);
	}

	public void updateMonthlyCount(Long memberId, int monthlyCount){
		Member member = memberAdapter.findById(memberId);
		MonthlyStats entity = adapter.getOrCreate(member);
		entity.updateMonthlyCount(monthlyCount);
        adapter.save(entity);
	}
}
