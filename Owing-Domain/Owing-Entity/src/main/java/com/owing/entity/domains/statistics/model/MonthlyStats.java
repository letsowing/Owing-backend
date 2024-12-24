package com.owing.entity.domains.statistics.model;

import java.time.LocalDate;

import com.owing.core.BaseEntity;
import com.owing.entity.domains.member.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MonthlyStats extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	private LocalDate month;
	private int monthlyCount;

	public void addMonthlyCount(int monthlyCount) {
        if(monthlyCount < 0){
            throw new IllegalArgumentException("T.T"); //fixme
        }
		this.monthlyCount += monthlyCount;
	}
}
