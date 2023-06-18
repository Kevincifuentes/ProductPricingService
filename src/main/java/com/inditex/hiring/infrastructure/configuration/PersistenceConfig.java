package com.inditex.hiring.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(value = "com.inditex.hiring.infrastructure.persistence.jpa")
@EnableJpaRepositories(basePackages = {
    "com.inditex.hiring.infrastructure.persistence.jpa.read",
    "com.inditex.hiring.infrastructure.persistence.jpa.write"
})
public class PersistenceConfig {
}
