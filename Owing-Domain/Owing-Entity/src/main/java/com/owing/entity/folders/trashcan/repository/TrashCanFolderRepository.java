package com.owing.entity.folders.trashcan.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

public interface TrashCanFolderRepository extends JpaRepository<TrashCanFolder, Long> {

	List<TrashCanFolder> findAllByProject_Id(Long projectId);

	void deleteAllByProject_Id(Long projectId);

	int deleteByCreatedAtBefore(LocalDateTime cutoffDateTime);
}
