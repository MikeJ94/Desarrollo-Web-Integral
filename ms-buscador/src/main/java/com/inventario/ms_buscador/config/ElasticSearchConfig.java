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
    // 1. Limpiar el host (quitar https:// y / al final)
    String cleanUri = uris.replace("https://", "").replace("http://", "");
    if (cleanUri.endsWith("/")) {
        cleanUri = cleanUri.substring(0, cleanUri.length() - 1);
    }
    
    // 2. Asegurar el puerto 443 para Bonsai SSL
    if (!cleanUri.contains(":")) {
        cleanUri += ":443";
    }

    // 3. Headers de compatibilidad
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
    headers.add("X-Elastic-Product", "Elasticsearch");

    return ClientConfiguration.builder()
            .connectedTo(cleanUri)
            .usingSsl()
            .withBasicAuth(username, password)
            .withHeaders(() -> headers)
            .withConnectTimeout(Duration.ofSeconds(20))
            .withSocketTimeout(Duration.ofSeconds(20))
            .build();
    }
}