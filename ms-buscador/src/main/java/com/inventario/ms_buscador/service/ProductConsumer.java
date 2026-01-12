package com.inventario.ms_buscador.service;

import com.inventario.ms_buscador.dto.ProductDTO;
import com.inventario.ms_buscador.model.Product;
import com.inventario.ms_buscador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductConsumer {

    @Autowired(required = false)
    private ProductRepository productRepository;

    @Bean
    public Consumer<ProductDTO> productconsumerRMQ() {
        return dto -> {
            try {
                Product product = Product.builder()
                        .id(dto.getId().toString())
                        .idSupplier(dto.getIdSupplier())
                        .nombre(dto.getNombre())
                        .categoria(dto.getCategoria())
                        .stock(dto.getStock())
                        .build();

                productRepository.save(product);
                System.out.println("====== EVENTO RECIBIDO ======");
                System.out.println("Producto sincronizado en Elastic: " + product.getNombre());
            } catch (Exception e) {
                System.err.println("Error procesando evento de RabbitMQ: " + e.getMessage());
            }
        };
    }
}