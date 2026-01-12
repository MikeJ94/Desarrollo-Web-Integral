package com.inventario.ms_operador.service;

import com.inventario.ms_operador.model.StockUpdateEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    private final StreamBridge streamBridge;
    
    // El nombre del binding (stock-updates-out) debe coincidir con application.yml
    private static final String OUTPUT_BINDING = "stock-updates-out"; 

    public EventProducerService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishStockUpdate(Long productId, int newStock) {
        StockUpdateEvent event = new StockUpdateEvent();
        event.setProductId(productId);
        event.setNewStock(newStock);
        
        // Envía el objeto de evento al canal configurado
        streamBridge.send(OUTPUT_BINDING, event); 
        System.out.println("-> Evento de Stock Publicado: Producto ID " + productId + " Stock: " + newStock);
    }
}