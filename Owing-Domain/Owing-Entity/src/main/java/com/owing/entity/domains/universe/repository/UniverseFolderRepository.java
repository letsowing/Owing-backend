package com.owing.entity.domains.universe.repository;

import org.springframework.stereotype.Repository;

import com.owing.core.dnd.folder.repository.BaseFolderEntityRepository;
import com.owing.entity.domains.universe.model.UniverseFolder;

@Repository
public interface UniverseFolderRepository extends BaseFolderEntityRepository<UniverseFolder> {
}
