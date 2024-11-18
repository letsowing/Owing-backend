package com.owing.entity.domains.statistics.repository;

import org.springframework.data.repository.CrudRepository;

import com.owing.entity.domains.statistics.model.Dashboard;

public interface DashboardRepository extends CrudRepository<Dashboard, String> {
}
