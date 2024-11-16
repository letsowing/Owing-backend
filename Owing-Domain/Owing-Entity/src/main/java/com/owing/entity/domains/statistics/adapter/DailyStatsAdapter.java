package com.owing.entity.domains.statistics.adapter;

import java.time.LocalDate;
import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.statistics.model.DailyStats;
import com.owing.entity.domains.statistics.repository.DailyStatsRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class DailyStatsAdapter {
	private final DailyStatsRepository repository;

	public DailyStats findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("T.T")); // fixme
	}

	public DailyStats getOrCreate(Member member) {
		return repository.findByMemberIdAndDate(member.getId(), LocalDate.now()).orElseGet(() ->
			DailyStats.builder()
				.member(member) //fixme
				.date(LocalDate.now())
				.build());
	}

	public DailyStats save(DailyStats entity) {
		return repository.save(entity);
	}

	public List<DailyStats> findByMemberAndPeriod(Member member, LocalDate start, LocalDate end) {
		return repository.findAllByMemberAndDateBetweenOrderByDate(member, start, end);
	}
}
