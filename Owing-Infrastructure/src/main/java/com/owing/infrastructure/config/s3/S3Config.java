package com.owing.infrastructure.config.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties s3Properties;

    @Bean
    public AwsCredentials basicAWSCredentials() {
        return AwsBasicCredentials.create(
            s3Properties.aws().credentials().accessKey(),
            s3Properties.aws().credentials().secretKey()
        );
    }

    @Bean
    public S3Client s3Client (AwsCredentials awsCredentials) {
        return S3Client.builder()
            .region(Region.of(s3Properties.aws().region()))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }

    @Bean
    public S3Presigner s3Presigner(AwsCredentials awsCredentials) {
        return S3Presigner.builder()
            .region(Region.of(s3Properties.aws().region()))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }

}
