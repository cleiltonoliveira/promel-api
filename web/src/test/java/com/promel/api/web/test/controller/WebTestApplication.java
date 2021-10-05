package com.promel.api.web.test.controller;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.promel.api")
@EnableJpaRepositories(basePackages = "com.promel.api.persistence")
@EntityScan(basePackages = "com.promel.api.persistence")
public class WebTestApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
