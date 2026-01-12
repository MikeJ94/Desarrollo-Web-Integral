package com.inventario.ms_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // Importante

@SpringBootApplication

@EnableEurekaServer // <-- Anotación que habilita este servicio como Servidor Eureka

public class MsEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsEurekaApplication.class, args);
    }
}