package com.owing.batch.launcher;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.model.Dashboard;
import com.owing.entity.domains.statistics.service.DailyStatsService;
import com.owing.entity.domains.statistics.service.DashboardService;
import com.owing.entity.domains.statistics.service.MonthlyStatsService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduleDashboard {
	private final DashboardService dashboardService;
	private final DailyStatsService dailyStatsService;
	private final MonthlyStatsService monthlyStatsService;

	@Scheduled(fixedDelay = 6000000)
	public void syncTextCount() {
		for (Dashboard dashboard : dashboardService.findAll()) { // fixme
			syncDailyStatistics(dashboard.getMember(), dashboard.getTodayCount());
			// syncMonthlyStatistics(member.getId(), month);
		}
	}

	public void syncDailyStatistics(Member member, int todayCount) {
		dailyStatsService.updateDailyCount(member, todayCount);
	}

	private void syncMonthlyStatistics(Member member, int monthlyCount) {
		monthlyStatsService.updateMonthlyCount(member, monthlyCount);
	}

}
