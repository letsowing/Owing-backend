package com.owing.node.domains.cast.repository;

import com.owing.node.domains.cast.model.dto.CastInfo;
import com.owing.node.domains.cast.model.Coordinate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CastNodeDeletedRepository {

    private final Neo4jClient neo4jClient;

    public CastInfo findDeletedById(Long castId) {
        final String cypher = """
                MATCH
                  (c:Cast{deleted: $isDeleted})
                WHERE
                  id(c)=$castId
                RETURN
                  id(c) as id, c as cast
                """;

        CastInfo castInfo = neo4jClient.query(cypher)
                .bind(true).to("isDeleted")
                .bind(castId).to("castId")
                .fetchAs(CastInfo.class).mappedBy((TypeSystem t, Record record) -> {
                    Long id = record.get("id").asLong();
                    Value castValue = record.get("cast");

                    Coordinate coordinate = new Coordinate(
                            castValue.get("coordinate.x").asInt(),
                            castValue.get("coordinate.y").asInt()
                    );
                    return new CastInfo(
                            id,
                            castValue.get("name").asString(),
                            castValue.get("age").asLong(),
                            castValue.get("gender").asString(),
                            castValue.get("role").asString(),
                            castValue.get("description").asString(),
                            castValue.get("imageUrl").asString(),
                            coordinate
                    );
                })
                .one()
                .orElse(null);

        return castInfo;
    }

    @PersistenceContext
    private EntityManager entityManager;

    //todo 준용이가 해다오..
//    public CastInfo findDeletedById(Long itemId) {
//        String sql = """
//                SELECT
//                    c.id AS id,
//                    c.name AS name,
//                    c.age AS age,
//                    c.gender AS gender,
//                    c.role AS role,
//                    c.description AS description,
//                    c.imageUrl AS imageUrl,
//                    c.coordinate.x AS coordinateX,
//                    c.coordinate.y AS coordinateY
//                FROM
//                    public.castNode c -- 스키마 명시
//                WHERE
//                    c.id = ?1
//                """;
//
//        try {
//            Object[] result = (Object[]) entityManager.createNativeQuery(sql)
//                    .setParameter(1, itemId)
//                    .getSingleResult();
//
//            Coordinate coordinate = new Coordinate(
//                    result[7] != null ? ((Number) result[7]).intValue() : null,
//                    result[8] != null ? ((Number) result[8]).intValue() : null
//            );
//
//            return new CastInfo(
//                    ((Number) result[0]).longValue(), // id
//                    (String) result[1],               // name
//                    result[2] != null ? ((Number) result[2]).longValue() : null, // age
//                    (String) result[3],               // gender
//                    (String) result[4],               // role
//                    (String) result[5],               // description
//                    (String) result[6],               // imageUrl
//                    coordinate                        // coordinate
//            );
//        } catch (NoResultException e) {
//            return null; // 결과가 없을 경우 null 반환
//        }
//    }
}
