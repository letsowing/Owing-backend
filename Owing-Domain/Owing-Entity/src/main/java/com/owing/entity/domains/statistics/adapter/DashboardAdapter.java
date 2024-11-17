package com.owing.entity.domains.statistics.adapter;

import java.time.LocalDate;
import java.time.YearMonth;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.statistics.model.Dashboard;
import com.owing.entity.domains.statistics.repository.DashboardRepository;
import com.owing.entity.domains.statistics.service.MonthlyStatsService;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class DashboardAdapter {
	private final DashboardRepository repository;
	private final MonthlyStatsService monthlyStatsService;

	public Dashboard getOrCreate(Long memberId){
		return repository.findById(memberId).orElseGet(() -> Dashboard.builder()
			.memberId(memberId)
			.date(LocalDate.now())
			.monthlyCount(monthlyStatsService.getMonthlyCount(memberId, YearMonth.now())) //fixme
			.build());
	}

	public Dashboard save(Dashboard dashboard){
		return repository.save(dashboard);
	}

	public Iterable<Dashboard> findAll(){
		return repository.findAll();
	}
}
