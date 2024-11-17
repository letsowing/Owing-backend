package com.owing.entity.domains.statistics.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import com.owing.entity.domains.member.model.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash("dashboard")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dashboard implements Serializable {

	@Id
	private Long memberId;

	private int todayCount;
	private int monthlyCount;
	private LocalDate date;

	private Member member; // fixme

	@TimeToLive
	private Long ttl;

	public void addDailyCount(int count) {
		this.todayCount += count;
	}

	public int getMonthlyAvgCount() {
		return monthlyCount / (date.getDayOfMonth() - 1); // fixme
	}
}
