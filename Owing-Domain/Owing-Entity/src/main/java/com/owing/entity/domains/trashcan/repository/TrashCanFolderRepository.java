package com.owing.entity.domains.trashcan.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

public interface TrashCanFolderRepository extends JpaRepository<TrashCanFolder, Long> {

	List<TrashCanFolder> findAllByProject_Id(Long projectId);

	void deleteAllByProject_Id(Long projectId);

	int deleteByCreatedAtBefore(LocalDateTime cutoffDateTime);

	Optional<TrashCanFolder> findByItemIdAndTableName(Long itemId, FolderType tableName);

	@Modifying
	@Query("UPDATE trash_can_folder t SET t.createdAt = CURRENT_TIMESTAMP WHERE t.id = :id")
	void updateCreatedAtById(Long id);
}
