package com.acme.center.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AgroDroneAnalyticsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroDroneAnalyticsPlatformApplication.class, args);
    }

}
