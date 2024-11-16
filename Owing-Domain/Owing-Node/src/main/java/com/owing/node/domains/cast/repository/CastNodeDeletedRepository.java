package com.owing.node.domains.cast.repository;

import com.owing.node.domains.cast.model.dto.CastInfo;
import com.owing.node.domains.cast.model.Coordinate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import jakarta.persistence.NoResultException;

@Repository
public class CastNodeDeletedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //todo 준용이가 해다오..
    public CastInfo findDeletedById(Long itemId) {
        String sql = """
                SELECT 
                    c.id AS id,
                    c.name AS name,
                    c.age AS age,
                    c.gender AS gender,
                    c.role AS role,
                    c.description AS description,
                    c.imageUrl AS imageUrl,
                    c.coordinate.x AS coordinateX,
                    c.coordinate.y AS coordinateY
                FROM 
                    public.castNode c -- 스키마 명시
                WHERE 
                    c.id = ?1
                """;

        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                    .setParameter(1, itemId)
                    .getSingleResult();

            Coordinate coordinate = new Coordinate(
                    result[7] != null ? ((Number) result[7]).intValue() : null,
                    result[8] != null ? ((Number) result[8]).intValue() : null
            );

            return new CastInfo(
                    ((Number) result[0]).longValue(), // id
                    (String) result[1],               // name
                    result[2] != null ? ((Number) result[2]).longValue() : null, // age
                    (String) result[3],               // gender
                    (String) result[4],               // role
                    (String) result[5],               // description
                    (String) result[6],               // imageUrl
                    coordinate                        // coordinate
            );
        } catch (NoResultException e) {
            return null; // 결과가 없을 경우 null 반환
        }
    }
}
