package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;

import java.time.Duration;

@Configuration
// Esta línea es VITAL para que no crashee buscando el ProductRepository
@EnableElasticsearchRepositories(basePackages = "com.inventario.ms_buscador.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String uris;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        String targetHost = uris.replace("https://", "").replace("http://", "").split("/")[0];
        
        if (!targetHost.contains(":") && uris.contains("https")) {
            targetHost += ":443";
        }

        HttpHeaders headers = new HttpHeaders();
        // Forzamos compatibilidad para evitar el error 400 en Bonsai
        headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(targetHost)
                .usingSsl()
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(10))
                .build();
    }
}