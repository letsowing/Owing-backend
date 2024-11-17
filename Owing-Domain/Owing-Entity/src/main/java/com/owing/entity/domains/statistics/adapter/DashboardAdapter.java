package com.owing.entity.domains.statistics.adapter;

import java.time.LocalDate;
import java.time.YearMonth;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.model.Dashboard;
import com.owing.entity.domains.statistics.repository.DashboardRepository;
import com.owing.entity.domains.statistics.service.MonthlyStatsService;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class DashboardAdapter {
	private final DashboardRepository repository;
	private final MonthlyStatsService monthlyStatsService;

	public Dashboard getOrCreate(Member member){
		return repository.findById(member.getId()).orElseGet(() -> Dashboard.builder()
			.memberId(member.getId())
			.date(LocalDate.now())
			.member(member)
			.monthlyCount(monthlyStatsService.getMonthlyCount(member, YearMonth.now())) //fixme
			.build());
	}

	public Dashboard save(Dashboard dashboard){
		return repository.save(dashboard);
	}

	public Iterable<Dashboard> findAll(){
		return repository.findAll();
	}
}
