package com.owing.infrastructure.config.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws")
record S3Properties(
        S3 s3,
        Boolean stackAuto,
        String regionStatic,
        Credentials credentials
) {
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