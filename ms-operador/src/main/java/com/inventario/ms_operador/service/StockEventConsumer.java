package com.inventario.ms_operador.service;

import com.inventario.ms_operador.model.StockUpdateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class StockEventConsumer {
    @Bean
    public Consumer<StockUpdateEvent> stockUpdateConsumer() {
        return event -> {
            System.out.println("===========================================");
            System.out.println(" [B5 - CONSUMER] Evento recibido desde RabbitMQ");
            System.out.println(" -> Procesando Producto ID: " + event.getProductId());
            System.out.println(" -> Nuevo Stock confirmado: " + event.getNewStock());
            System.out.println("===========================================");
            
            // Aquí podrías, por ejemplo, disparar una alerta si el stock es muy bajo
        };
    }
}
