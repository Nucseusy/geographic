package com.br.microservice.geographic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class GeographicApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeographicApplication.class, args);
    }

}
