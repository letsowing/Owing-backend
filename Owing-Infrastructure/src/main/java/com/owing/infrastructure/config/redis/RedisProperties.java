package com.owing.infrastructure.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
record RedisProperties(
        String host,
        String port,
        String password,
        String timeout
) {

    public Integer portAsInt() {
        return Integer.parseInt(port);
    }
    public Integer timeoutAsInt() {
        return Integer.parseInt(timeout);
    }
}
