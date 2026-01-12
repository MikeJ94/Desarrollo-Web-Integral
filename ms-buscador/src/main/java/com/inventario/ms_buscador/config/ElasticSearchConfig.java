package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris:}")
    private String uris;

    @Value("${spring.elasticsearch.username:}")
    private String username;

    @Value("${spring.elasticsearch.password:}")
    private String password;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        System.out.println("DEBUG: Intentando conectar a: " + uris);
        
        // Si la variable está vacía, usamos un fallback para que no explote con null
        String targetHost = "localhost:9200";
        
        if (uris != null && !uris.isEmpty()) {
            targetHost = uris.replace("https://", "").replace("http://", "").split("/")[0];
            if (!targetHost.contains(":") && uris.contains("https")) {
                targetHost += ":443";
            }
        }

        System.out.println("DEBUG: Host final procesado: " + targetHost);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(targetHost)
                .usingSsl()
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .withConnectTimeout(10000) // 10 segundos
                .withSocketTimeout(10000)
                .build();
    }
}