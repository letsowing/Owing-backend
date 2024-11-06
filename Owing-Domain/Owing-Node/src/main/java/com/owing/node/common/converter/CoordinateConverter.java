package com.owing.node.common.converter;

import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.model.Coordinate;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jConversionService;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyToMapConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public class CoordinateConverter implements Neo4jPersistentPropertyToMapConverter<String, Coordinate> {

    @NonNull @Override
    public Map<String, Value> decompose(@Nullable Coordinate property, @NonNull Neo4jConversionService neo4jConversionService) {
        Value x = Values.value(CastConstant.DEFAULT_COORDINATE_X);
        Value y = Values.value(CastConstant.DEFAULT_COORDINATE_Y);

        if (property != null) {
            if (property.x() != null) {
                x = Values.value(property.x());
            }
            if (property.y() != null) {
                y = Values.value(property.y());
            }
        }

        return Map.of("x", x, "y", y);
    }

    @NonNull @Override
    public Coordinate compose(Map<String, Value> source, @NonNull Neo4jConversionService neo4jConversionService) {
        int x = 0;
        int y = 0;
        if (!source.isEmpty()) {
            x = source.get("x").asInt();
            y = source.get("y").asInt();
        }
        return new Coordinate(x, y);
    }
}


