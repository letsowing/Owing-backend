package com.owing.entity.domains.statistics.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.statistics.adapter.DashboardAdapter;
import com.owing.entity.domains.statistics.model.Dashboard;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DashboardService {
	private final DashboardAdapter dashboardAdapter;

	public Dashboard getDashBoard(Long memberId) {
		return dashboardAdapter.getOrCreate(memberId);
	}

	public void updateTodayCount(Long memberId, int delta) {
		Dashboard dashboard = dashboardAdapter.getOrCreate(memberId);
		dashboard.addDailyCount(delta);
		dashboardAdapter.save(dashboard);
	}

	public Iterable<Dashboard> findAll() {
		return dashboardAdapter.findAll();
	}

}
