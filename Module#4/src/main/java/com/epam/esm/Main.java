package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@EntityScan(basePackages = {"com.epam.esm"})
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
