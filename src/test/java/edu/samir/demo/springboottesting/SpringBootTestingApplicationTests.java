package edu.samir.demo.springboottesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class SpringBootTestingApplicationTests {

    @Container
    public static PostgreSQLContainer psqlContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password");

    // Dynamically populate environment properties
    @DynamicPropertySource
    static void updateProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", psqlContainer::getUsername);
        registry.add("spring.datasource.password", psqlContainer::getPassword);
    }

    @Test
    void contextLoads() {
        System.out.println("Context loaded successfully");
    }

}
