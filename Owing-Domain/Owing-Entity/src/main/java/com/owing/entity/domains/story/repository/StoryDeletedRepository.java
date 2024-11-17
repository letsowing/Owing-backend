package com.owing.entity.domains.story.repository;

import com.owing.entity.domains.story.model.dto.StoryInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class StoryDeletedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public StoryInfo findDeletedById(Long itemId) {
        String sql = "SELECT s.id AS storyId, s.name AS name, s.description AS description, " +
                "s.text_count AS textCount, sc.content AS content " +
                "FROM story s " +
                "LEFT JOIN story_content sc ON s.story_content_id = sc.id " +
                "WHERE s.id = " + itemId; // 값 직접 삽입 (SQL 인젝션 주의)

        Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                .getSingleResult();

        return new StoryInfo(
                ((Number) result[0]).longValue(), // storyId
                (String) result[1],               // name
                (String) result[2],               // description
                (Integer) result[3],              // textCount
                (String) result[4]                // content
        );
    }
}
