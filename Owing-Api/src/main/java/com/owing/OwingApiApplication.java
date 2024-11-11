package com.owing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@ConfigurationPropertiesScan
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class OwingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwingApiApplication.class, args);
    }

}
