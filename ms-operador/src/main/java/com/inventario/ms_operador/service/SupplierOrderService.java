// package com.inventario.ms_operador.service;

// import com.inventario.ms_operador.dto.OrderRequestDTO;
// import com.inventario.ms_operador.model.Product;
// import com.inventario.ms_operador.model.SupplierOrder;
// import com.inventario.ms_operador.repository.ProductRepository;
// import com.inventario.ms_operador.repository.SupplierOrderRepository;
// import com.inventario.ms_operador.repository.SupplierRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// @RequiredArgsConstructor
// public class SupplierOrderService {

//     private final SupplierOrderRepository orderRepository;
//     private final SupplierRepository supplierRepository;
//     private final ProductRepository productRepository;
//     private final EventProducerService eventProducerService; // Inyectamos tu productor de eventos

//     @Transactional
//     public SupplierOrder createOrder(OrderRequestDTO request) {
//         // 1. FORZAR LOG DE ENTRADA
//         System.out.println("VALORES RECIBIDOS - Prod: " + request.getIdProduct() + " Cant: " + request.getQuantity());

//         // 1. Validar Proveedor
//         if (!supplierRepository.existsById(request.getIdSupplier())) {
//             throw new RuntimeException("Proveedor no encontrado con ID: " + request.getIdSupplier());
//         }

//         // 2. Validar y Obtener Producto
//         Product product = productRepository.findById(request.getIdProduct())
//                 .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + request.getIdProduct()));

//         // 3. Crear y Guardar la Orden
//         /*SupplierOrder newOrder = new SupplierOrder(
//                 request.getIdProduct(),
//                 request.getIdSupplier(),
//                 request.getQuantity()
//                  ); */
//                 // Usamos Setters para evitar cualquier error de posición en el constructor
//                 SupplierOrder newOrder = new SupplierOrder();
//                 newOrder.setIdProduct(request.getIdProduct());
//                 newOrder.setIdSupplier(request.getIdSupplier());
//                 newOrder.setQuantity(request.getQuantity()); // <-- Esto ahora sí llegará a la BD
       
//         SupplierOrder savedOrder = orderRepository.save(newOrder);

//         // 4. Lógica de Stock: Actualizar el stock del producto
//         // Al ser una orden al proveedor, el stock debería aumentar
//         // IMPORTANTE: Usamos la cantidad que viene del request directamente
//         //int nuevoStock = product.getStock() + request.getQuantity();
//         int cantidadAsumar = request.getQuantity();
//         int stockActual = product.getStock();
//         int nuevoStock = stockActual + cantidadAsumar;

//         System.out.println("CALCULO: " + stockActual + " + " + cantidadAsumar + " = " + nuevoStock);

//         product.setStock(nuevoStock);
//         productRepository.save(product);

//         // 5. B5 - Notificar evento de actualización de stock
//         eventProducerService.publishStockUpdate(product.getId(), nuevoStock);

//         return savedOrder;
//     }
// }

package com.inventario.ms_operador.service;

import com.inventario.ms_operador.dto.OrderRequestDTO;
import com.inventario.ms_operador.model.Product;
import com.inventario.ms_operador.model.SupplierOrder;
import com.inventario.ms_operador.repository.ProductRepository;
import com.inventario.ms_operador.repository.SupplierOrderRepository;
import com.inventario.ms_operador.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierOrderService {

    private final SupplierOrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductService productService; // Inyectamos el servicio de productos

    @Transactional
    public SupplierOrder createOrder(OrderRequestDTO request) {
        log.info("Procesando nueva orden: Producto ID {}, Cantidad {}", request.getIdProduct(), request.getQuantity());

        // 1. Validar Proveedor
        if (!supplierRepository.existsById(request.getIdSupplier())) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + request.getIdSupplier());
        }

        // 2. Crear y Guardar la Orden en la DB
        SupplierOrder newOrder = new SupplierOrder();
        newOrder.setIdProduct(request.getIdProduct());
        newOrder.setIdSupplier(request.getIdSupplier());
        newOrder.setQuantity(request.getQuantity());
        newOrder.setOrderDate(LocalDateTime.now()); // Aseguramos la fecha
       
        SupplierOrder savedOrder = orderRepository.save(newOrder);

        // 3. Lógica de Stock y Sincronización con el Buscador
        // Llamamos al método centralizado en ProductService
        // Esto hace 3 cosas: Busca el producto, suma el stock y NOTIFICA a RabbitMQ
        productService.registrarCompra(request.getIdProduct(), request.getQuantity());

        log.info("Orden guardada y stock sincronizado exitosamente.");
        return savedOrder;
    }
}