package com.owing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class OwingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwingApiApplication.class, args);
    }

}
