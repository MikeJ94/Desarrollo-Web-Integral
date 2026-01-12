package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
// IMPORT CLAVE: Usamos el HttpHeaders específico de Spring Data Elasticsearch
import org.springframework.data.elasticsearch.support.HttpHeaders; 
import org.springframework.lang.NonNull;

import java.util.function.Supplier;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris:http://localhost:9200}")
    private String uris;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        // Limpiamos la URL (host:puerto)
        String cleanUri = uris.replace("https://", "").replace("http://", "");

        // Usamos la clase HttpHeaders que el driver espera
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(cleanUri)
                .usingSsl()
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .build();
    }
}