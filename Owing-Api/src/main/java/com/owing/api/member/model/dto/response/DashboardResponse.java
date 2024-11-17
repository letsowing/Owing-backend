package com.owing.api.member.model.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.owing.entity.domains.statistics.model.DailyStats;
import com.owing.entity.domains.statistics.model.Dashboard;

import lombok.Builder;

@Builder
public record DashboardResponse(
	int todayCount,
	int monthlyCount,
	int monthlyAvgCount,
	List<DailyCountResponse> graph
) {
	public static DashboardResponse of(Dashboard dashboard, List<DailyStats> daily) {
		return DashboardResponse.builder()
			.todayCount(dashboard.getTodayCount())
			.monthlyCount(dashboard.getMonthlyCount())
			.monthlyAvgCount(dashboard.getMonthlyAvgCount())
			.graph(daily.stream()
                .map(DailyCountResponse::of)
                .toList())
			.build();
	}

	@Builder
	public record DailyCountResponse(
        LocalDate date,
        int dailyCount
    ) {
		public static DailyCountResponse of(DailyStats daily) {
			return DailyCountResponse.builder()
                .date(daily.getDate())
                .dailyCount(daily.getDailyCount())
                .build();
		}
	}
}
