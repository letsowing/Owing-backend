package com.owing.api.universe;

import com.owing.OwingApiApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
        classes = OwingApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
public class ServiceConnectionIntegrationTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15.2")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @Container
    @ServiceConnection
    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>("redis:7.0")
                    .withExposedPorts(6379);
}