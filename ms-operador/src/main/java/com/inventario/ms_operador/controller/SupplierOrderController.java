package com.inventario.ms_operador.controller;

import com.inventario.ms_operador.dto.OrderRequestDTO;
import com.inventario.ms_operador.model.SupplierOrder;
import com.inventario.ms_operador.repository.SupplierOrderRepository; // Añadimos e
import com.inventario.ms_operador.service.SupplierOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class SupplierOrderController {

    private final SupplierOrderService orderService;
    private final SupplierOrderRepository orderRepository; // Inyectamos el repo

    // POST ya funciona (el que probaste)
    @PostMapping
    public ResponseEntity<SupplierOrder> crear(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // --- NUEVOS MÉTODOS GET ---

    // Obtener todas las órdenes: GET http://localhost:8082/api/ordenes
    @GetMapping
    public ResponseEntity<List<SupplierOrder>> listarTodas() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    // Obtener una por ID: GET http://localhost:8082/api/ordenes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SupplierOrder> obtenerPorId(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}