package com.owing.infrastructure.config.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud")
record S3Properties(
        AWS aws,
        S3 s3,
        Boolean stackAuto
) {
    record AWS(
        String region,
        Credentials credentials
    ) {}
    record S3(
            String bucket,
            Directory directory
    ) {}
    record Directory(
            String project,
            String casting,
            String universe
    ) {}
    record Credentials(
        String accessKey,
        String secretKey
    ) {}

}