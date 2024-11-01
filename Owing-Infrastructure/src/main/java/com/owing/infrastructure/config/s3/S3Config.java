package com.owing.infrastructure.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
class S3Config {

    private final S3Properties s3Properties;

//    @Bean
//    @Primary
//    public BasicAWSCredentials awsCredentialsProvider(){
//
//        return new BasicAWSCredentials(s3Properties.credentials().accessKey(), s3Properties.credentials().secretKey());
//    }
//
//    @Bean
//    public AmazonS3 amazonS3() {
//
//        return AmazonS3ClientBuilder.standard()
//                .withRegion(s3Properties.regionStatic())
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
//                .build();
//    }

    @Bean
    @Primary
    public AwsRegionProvider customRegionProvider() {
        return () -> Region.of(s3Properties.regionStatic());
    }

    @Bean
    @Primary
    public AwsCredentialsProvider customAwsCredentialsProvider() {
        AwsCredentials awsCredentials = new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return s3Properties.credentials().accessKey();
            }

            @Override
            public String secretAccessKey() {
                return s3Properties.credentials().secretKey();
            }
        };

        return () -> awsCredentials;
    }

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider awsCredentialsProvider = customAwsCredentialsProvider();
        Region region = customRegionProvider().getRegion();
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(region)
                .build();
    }

}
