package com.inventario.ms_buscador.config;

import co.elastic.clients.transport.TransportOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

import java.time.Duration;

@Configuration
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
        // Limpiamos el host quitando protocolos y slash final
        String cleanUri = uris.replace("https://", "").replace("http://", "");
        if (cleanUri.endsWith("/")) {
            cleanUri = cleanUri.substring(0, cleanUri.length() - 1);
        }
        
        // Forzamos el puerto 443 para SSL
        if (!cleanUri.contains(":")) {
            cleanUri += ":443";
        }

        return ClientConfiguration.builder()
                .connectedTo(cleanUri)
                .usingSsl()
                .withBasicAuth(username, password)
                // Agregamos tiempo de espera extra para Railway/Bonsai
                .withConnectTimeout(Duration.ofSeconds(30))
                .withSocketTimeout(Duration.ofSeconds(30))
                .build();
    }

    // AGREGAMOS ESTO: Es el "truco" para saltar la validación del encabezado de producto
    @Override
    public TransportOptions transportOptions() {
        return super.transportOptions().toBuilder()
                .addHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7")
                .addHeader("X-Elastic-Product", "Elasticsearch")
                .build();
    }
}