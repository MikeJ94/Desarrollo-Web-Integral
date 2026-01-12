//C. El Servicio (Lógica + RabbitMQ)
package com.inventario.ms_operador.service;

import com.inventario.ms_operador.model.Product;
import com.inventario.ms_operador.model.CustomerShipment; // Debes crear esta entidad
import com.inventario.ms_operador.repository.ProductRepository;
import com.inventario.ms_operador.repository.CustomerShipmentRepository; // Debes crear este repo
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CustomerShipmentRepository shipmentRepository;
    private final StreamBridge streamBridge; // Envía mensajes a RabbitMQ

    public Product guardarProducto(Product producto) {
        // 1. Persistencia en base de datos
        Product guardado = repository.save(producto);
        notificarRabbitMQ(guardado);
        //log.info("Producto guardado con ID: {}", guardado.getId());
        
        // 2. Emisión de mensaje a RabbitMQ usando tu binding del yml
        //boolean enviado = streamBridge.send("stock-updates-out", guardado);

        // Añade esta línea para verificar la base de datos actual:
        //log.info("Total de productos en DB ahora: {}", repository.count());
        //boolean enviado = true; // Forzamos esto para que el código compile y no dé error abajo
        /* 
        if (enviado) {
            log.info("Notificación enviada a RabbitMQ exitosamente");
        } else {
            log.error("Falló el envío a RabbitMQ");
        } */

        /* if (enviado) {
        log.info("Simulación: Notificación enviada exitosamente");
        } */
        
        return guardado;
    }

    // NUEVO: Lógica de salida de mercancía
    @Transactional
    public void registrarEnvio(Long productId, Long customerId, Integer cantidad) {
        // 1. Validar stock
        Product producto = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        // 2. Restar stock y guardar
        producto.setStock(producto.getStock() - cantidad);
        repository.save(producto);

        // 3. Registrar en tabla histórica de envíos
        CustomerShipment shipment = new CustomerShipment();
        shipment.setIdProduct(productId);
        shipment.setIdCustomer(customerId);
        shipment.setQuantity(cantidad);
        shipment.setShipmentDate(LocalDateTime.now());
        shipmentRepository.save(shipment);

        log.info("Envío registrado: {} unidades del producto {}", cantidad, productId);

        // 4. Sincronizar con Elasticsearch (ms-buscador)
        notificarRabbitMQ(producto);
    }

    private void notificarRabbitMQ(Product producto) {
        boolean enviado = streamBridge.send("stock-updates-out", producto);
        if (enviado) {
            log.info("Notificación enviada a RabbitMQ para producto ID: {}", producto.getId());
        } else {
            log.error("Falló el envío a RabbitMQ para producto ID: {}", producto.getId());
        }
    }

    public List<Product> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public void registrarCompra(Long productId, Integer cantidad) {
    // 1. Buscar el producto
    Product producto = repository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado para reabastecimiento"));

    // 2. Sumar el stock y guardar en DB
    producto.setStock(producto.getStock() + cantidad);
    repository.save(producto);

    log.info("Compra registrada: Se sumaron {} unidades al producto ID: {}", cantidad, productId);

    // 3. ¡ESTO ES LO QUE TE FALTA! Sincronizar con Elasticsearch
    notificarRabbitMQ(producto);
  }
}