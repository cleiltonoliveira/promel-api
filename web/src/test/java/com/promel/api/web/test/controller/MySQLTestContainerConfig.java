package com.promel.api.web.test.controller;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class MySQLTestContainerConfig {

    static MySQLContainer mySQLContainer;

    static {
        mySQLContainer = new MySQLContainer<>("mysql:8:0.11")
                .withDatabaseName("promel")
                .withUsername("promelapi")
                .withPassword("promelpass").withReuse(true);
        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
}