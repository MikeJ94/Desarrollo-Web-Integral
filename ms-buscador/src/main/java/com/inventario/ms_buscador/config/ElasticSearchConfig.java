package com.inventario.ms_buscador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;
import java.net.URI;

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
        // USAMOS URI PARA EXTRAER EL HOST Y PUERTO CORRECTAMENTE
        URI uri = URI.create(uris);
        String host = uri.getHost();
        int port = uri.getPort();

        // Si el puerto no viene en la URL (Bonsai usa 443 para https)
        if (port == -1) {
            port = uris.startsWith("https") ? 443 : 80;
        }

        String finalAddress = host + ":" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Elastic-Product", "Elasticsearch");

        return ClientConfiguration.builder()
                .connectedTo(finalAddress) // Esto enviará "host.bonsai.net:443"
                .usingSsl()
                .withBasicAuth(username, password)
                .withHeaders(() -> headers)
                .build();
    }
}