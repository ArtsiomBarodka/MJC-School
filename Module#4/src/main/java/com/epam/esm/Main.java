package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Main.
 */
@PropertySource("classpath:application.yaml")
@EntityScan(basePackages = {"com.epam.esm"})
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
