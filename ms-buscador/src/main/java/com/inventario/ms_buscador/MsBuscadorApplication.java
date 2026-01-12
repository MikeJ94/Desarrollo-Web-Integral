package com.inventario.ms_buscador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude = {
    ElasticsearchClientAutoConfiguration.class, 
    ElasticsearchDataAutoConfiguration.class
})

@EnableDiscoveryClient // Habilita el registro en Eureka Serv
//@EnableElasticsearchRepositories(basePackages = "com.inventario.ms_buscador.repository")
public class MsBuscadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBuscadorApplication.class, args);
	}

}
