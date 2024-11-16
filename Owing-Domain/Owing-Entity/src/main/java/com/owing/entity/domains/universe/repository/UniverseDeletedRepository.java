package com.owing.entity.domains.universe.repository;

import com.owing.entity.domains.story.model.dto.StoryInfo;
import com.owing.entity.domains.universe.model.dto.UniverseInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UniverseDeletedRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UniverseInfo findDeletedById(Long itemId) {
        String sql = "SELECT u.id AS universeId, u.name AS name, u.description AS description, u.image_url AS imageUrl " +
                "FROM universe u " +
                "WHERE u.id = " + itemId; // 값 직접 삽입 (SQL 인젝션 주의)

        Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                .getSingleResult();

        return new UniverseInfo(
                ((Number) result[0]).longValue(), // universeId
                (String) result[1],               // name
                (String) result[2],               // description
                (String) result[3]              // imageUrl
        );
    }
}
