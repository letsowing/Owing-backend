package com.owing.core.dnd.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

@NoRepositoryBean
public interface BaseFileEntityRepository<T extends BaseFile<F>, F extends BaseFolder> extends BaseFileRepository<T, F>,
	JpaRepository<T, Long> {
	@Query(value = "select f from #{#entityName} f where f.folder.id = :folderId order by f.position")
	List<T> findByParentId(Long folderId);

	List<T> findAllByFolderIdOrderByPositionAsc(Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.folder.id = :folderId")
	void decrementPositionAfter(Long position, Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position >= :position and T.folder.id = :folderId")
	void incrementPositionAfter(Long position, Long folderId);

	@Query("SELECT COALESCE(MAX(T.position), '-1') FROM #{#entityName} T WHERE T.folder.id = :folderId")
	Long getMaxPositionByParentId(Long folderId);

	@Modifying
	@Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.folder.id = :folderId")
	void decrementPositionBetween(Long start, Long end, Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.folder.id = :folderId")
	void incrementPositionBetween(Long start, Long end, Long folderId);
}
