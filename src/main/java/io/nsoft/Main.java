package io.nsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "io.nsoft")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}