package com.owing.batch.launcher;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

	@Scheduled(cron = "0 30 * * * ?", zone = "Asia/Seoul")
	public void syncTextCount() {
		LocalDate today = LocalDate.now();
		for (Dashboard dashboard : dashboardService.findAll()) { // fixme
			if(dashboard.getDate().isEqual(today)) {
				syncDailyStatistics(dashboard.getMemberId(), dashboard.getTodayCount(), today);
				// syncMonthlyStatistics(member.getId(), month);
			}
		}
	}

	@Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
	public void resetDailyCache() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDate month = yesterday.withDayOfMonth(1);


		for (Dashboard dashboard : dashboardService.findAll()) {
			if (dashboard.getDate().isEqual(yesterday)){
				dailyStatsService.updateDailyCount(dashboard.getMemberId(), dashboard.getTodayCount(), yesterday);
				monthlyStatsService.updateMonthlyCount(dashboard.getMemberId(), dashboard.getTodayCount(), month);
				dashboardService.deleteDashboard(dashboard);
			}
		}
	}

	public void syncDailyStatistics(Long memberId, int todayCount, LocalDate date) {
		dailyStatsService.updateDailyCount(memberId, todayCount, date);
	}

	private void syncMonthlyStatistics(Long memberId, int monthlyCount, LocalDate localDate) {
		monthlyStatsService.updateMonthlyCount(memberId, monthlyCount, localDate);
	}

}
