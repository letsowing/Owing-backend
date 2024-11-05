package com.owing.entity.domains.universe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.entity.domains.universe.model.Universe;

public interface UniverseRepository extends JpaRepository<Universe, Long> {
}
