package com.inditex.hiring.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.inditex.hiring.infrastructure.configuration")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
