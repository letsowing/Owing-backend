package com.owing.entity.domains.project.repository;


import com.owing.entity.domains.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findById(Long id);

    List<Project> findAllByMember_Id(Long id);
}
