package com.owing.batch.launcher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ScheduleOldTrash {
	private final TrashCanRepository trashCanRepository;
	private final TrashCanFolderRepository trashCanFolderRepository;

	@Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Seoul")
	public void cleanOldTrash() {
		log.info("Old trash cleaning schedule started");

		LocalDateTime cutoffDateTime = LocalDate.now().minusWeeks(2).atStartOfDay();

		int deletedCount = trashCanRepository.deleteByCreatedAtBefore(cutoffDateTime);
		deletedCount += trashCanFolderRepository.deleteByCreatedAtBefore(cutoffDateTime);

		log.info("Deleted {} trash items older than 2 weeks", deletedCount);
	}
}
