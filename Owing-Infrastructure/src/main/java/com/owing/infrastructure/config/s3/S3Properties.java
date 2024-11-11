package com.owing.infrastructure.config.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud")
public record S3Properties(
        AWS aws,
        S3 s3,
        Boolean stackAuto
) {
    public record AWS(
        String region,
        Credentials credentials
    ) {}
    public record S3(
            String bucket,
            Directory directory
    ) {}
    public record Directory(
            String project,
            String casting,
            String universe
    ) {}
    public record Credentials(
        String accessKey,
        String secretKey
    ) {}

}