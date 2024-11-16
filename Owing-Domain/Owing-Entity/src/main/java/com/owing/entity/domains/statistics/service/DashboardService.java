package com.owing.entity.domains.statistics.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.adapter.DashboardAdapter;
import com.owing.entity.domains.statistics.model.Dashboard;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DashboardService {
	private final DashboardAdapter dashboardAdapter;

	public Dashboard getDashBoard(Member member) {
		return dashboardAdapter.getOrCreate(member);
	}

	public void updateTodayCount(Member member, int delta) {
		Dashboard dashboard = dashboardAdapter.getOrCreate(member);
		dashboard.addDailyCount(delta);
		dashboardAdapter.save(dashboard);
	}

	public Iterable<Dashboard> findAll() {
		return dashboardAdapter.findAll();
	}

}
