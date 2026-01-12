package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;

import java.time.Duration;

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
        // 1. Limpieza profunda de la URI para obtener solo el HOST
        // Bonsai devuelve a veces la URL con el puerto incluido o con / al final
        String targetHost = uris.replace("https://", "")
                               .replace("http://", "")
                               .split("/")[0]; // Nos quedamos solo con la parte del dominio:puerto
        
        // 2. Forzar puerto 443 si es HTTPS y no hay puerto explícito
        if (!targetHost.contains(":") && uris.contains("https")) {
            targetHost += ":443";
        }

        System.out.println("DEBUG: Conectando a Bonsai en host: " + targetHost);

        // 3. Configuración de Headers de compatibilidad
        // Esto le dice a Bonsai (ES 7.x) que ignore los comandos de ES 8.x
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(targetHost)
                .usingSsl() // Bonsai requiere SSL
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(10))
                .build();
    }
}