package com.owing.entity.domains.statistics.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash("dashboard")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dashboard {
	@Id
	private String id;

	private Long memberId;
	private int todayCount;
	private int monthlyCount;
	private LocalDate date;

	@TimeToLive
	private Long ttl;

	@Builder
	public Dashboard(String id, Long memberId, int todayCount, int monthlyCount, LocalDate date, Long ttl) {
		this.id = id;
		this.memberId = memberId;
		this.todayCount = todayCount;
		this.monthlyCount = monthlyCount;
		this.date = date;
		this.ttl = ttl;
	}

	public void addDailyCount(int count) {
		this.todayCount += count;
	}

	public int getMonthlyAvgCount() {
		return monthlyCount / (date.getDayOfMonth() - 1); // fixme
	}
}
