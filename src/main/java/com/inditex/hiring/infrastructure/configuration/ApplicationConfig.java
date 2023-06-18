package com.inditex.hiring.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    value = "com.inditex.hiring.application"
)
public class ApplicationConfig {

}