package com.owing.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@ConfigurationPropertiesScan
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class OwingInfrastructureApplication implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;

//    public static void main(String[] args) {
//        SpringApplication.run(OwingInfrastructureApplication.class, args);
//    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReady status: " + Arrays.toString(environment.getActiveProfiles()));
    }

}
