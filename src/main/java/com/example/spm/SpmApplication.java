package com.example.spm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpmApplication.class, args);
    }

}
