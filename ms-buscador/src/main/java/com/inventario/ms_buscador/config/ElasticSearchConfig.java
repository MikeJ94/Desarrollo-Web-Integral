package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;

@Configuration
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
        // Limpieza manual y segura para Bonsai
        // Quitamos el protocolo y cualquier diagonal final
        String cleanAddress = uris.replace("https://", "")
                                  .replace("http://", "")
                                  .replace("/", "");

        // Bonsai siempre corre en el puerto 443 para HTTPS si no se especifica
        if (!cleanAddress.contains(":")) {
            cleanAddress += ":443";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(cleanAddress)
                .usingSsl()
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .build();
    }
}