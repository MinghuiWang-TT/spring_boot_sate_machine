package com.alex.fsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alex.fsm"})
public class ApplicationContext {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationContext.class, args);
  }
}
