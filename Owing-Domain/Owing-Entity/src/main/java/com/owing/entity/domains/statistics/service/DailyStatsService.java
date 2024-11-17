package com.owing.entity.domains.statistics.service;

import java.time.LocalDate;
import java.util.List;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.adapter.MemberAdapter;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.adapter.DailyStatsAdapter;
import com.owing.entity.domains.statistics.model.DailyStats;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DailyStatsService {
	private final DailyStatsAdapter adapter;
	private final MemberAdapter memberAdapter;

	public void updateDailyCount(Long memberId, int delta) {
		Member member = memberAdapter.findById(memberId);

		DailyStats entity = adapter.getOrCreate(member);
		entity.updateDailyCount(delta);
		adapter.save(entity);
	}

	// 일주일 글자수 통계 용
	public List<DailyStats> getDurationStatistics(Long memberId, LocalDate start, LocalDate end) {
		return adapter.findByMemberAndPeriod(memberId, start, end);
	}

}
