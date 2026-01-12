package com.inventario.ms_operador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableDiscoveryClient // Habilita el registro en Eureka Server

// Agregamos estas dos para asegurar la visibilidad
@EnableJpaRepositories(basePackages = "com.inventario.ms_operador.repository")
@EntityScan(basePackages = "com.inventario.ms_operador.model")

public class MsOperadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOperadorApplication.class, args);
	}

}
