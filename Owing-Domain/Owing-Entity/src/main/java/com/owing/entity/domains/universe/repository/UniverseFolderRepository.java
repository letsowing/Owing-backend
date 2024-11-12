package com.owing.entity.domains.universe.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owing.core.dnd.folder.repository.BaseFolderEntityRepository;
import com.owing.entity.domains.universe.model.UniverseFolder;

@Repository
public interface UniverseFolderRepository extends BaseFolderEntityRepository<UniverseFolder> {
	@Modifying
	@Query(value = "UPDATE universe_folder SET deleted = false WHERE id = :itemId", nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);
}
