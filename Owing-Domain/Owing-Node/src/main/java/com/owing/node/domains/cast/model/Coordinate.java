package com.owing.node.domains.cast.model;

import com.owing.node.common.constant.CastConstant;

import java.util.concurrent.ThreadLocalRandom;

public record Coordinate(
        Integer x,
        Integer y
) {

    public static int randomCoordinate() {
        return ThreadLocalRandom.current().nextInt(0, CastConstant.MAX_COORDINATE +1);
    }

}
