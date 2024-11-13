package com.owing.entity.domains.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owing.core.dnd.file.repository.BaseFileEntityRepository;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;

public interface UniverseRepository extends BaseFileEntityRepository<Universe, UniverseFolder> {
	@Modifying
	@Query(value = "UPDATE universe SET deleted = false WHERE id = :itemId", nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);

	List<Universe> findByFolder_ProjectId(Long projectId);
}
