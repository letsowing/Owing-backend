package com.owing.entity.domains.member.repository;

import com.owing.entity.domains.member.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
