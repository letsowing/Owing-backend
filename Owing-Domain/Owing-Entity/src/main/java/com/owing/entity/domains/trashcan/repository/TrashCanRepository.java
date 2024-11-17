package com.owing.entity.domains.trashcan.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.entity.domains.trashcan.model.TrashCan;

public interface TrashCanRepository extends JpaRepository<TrashCan, Long> {

	List<TrashCan> findAllByTrashCanFolder_Id(Long trashCanFolderId);

	int deleteByCreatedAtBefore(LocalDateTime cutoffDate);
}
