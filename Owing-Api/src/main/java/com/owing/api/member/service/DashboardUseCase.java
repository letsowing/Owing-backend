package com.owing.api.member.service;

import java.time.LocalDate;
import java.util.List;

import com.owing.common.util.MemberUtils;
import com.owing.api.member.model.dto.response.DashboardResponse;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.statistics.model.DailyStats;
import com.owing.entity.domains.statistics.model.Dashboard;
import com.owing.entity.domains.statistics.service.DailyStatsService;
import com.owing.entity.domains.statistics.service.DashboardService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DashboardUseCase {

	private final DashboardService dashboardService;
	private final DailyStatsService dailyStatsService;
	private final MemberUtils memberUtils;

	public DashboardResponse execute() {
		Long memberId = memberUtils.getCurrentMemberId();
		Dashboard dashboard = dashboardService.getDashBoard(memberId);

		LocalDate start = LocalDate.now().minusDays(7);
		LocalDate end = LocalDate.now().minusDays(1);

		List<DailyStats> graph = dailyStatsService.getDurationStatistics(memberId, start, end);

		return DashboardResponse.of(dashboard, graph);
	}
}
