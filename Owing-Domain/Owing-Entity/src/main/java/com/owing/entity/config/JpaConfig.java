package com.owing.entity.config;

import com.owing.entity.EntityPackageLocation;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = EntityPackageLocation.class)
@EnableJpaRepositories(basePackageClasses = EntityPackageLocation.class)
public class JpaConfig {
}
