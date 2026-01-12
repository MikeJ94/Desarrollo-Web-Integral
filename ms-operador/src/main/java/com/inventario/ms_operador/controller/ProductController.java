//D. El Controlador (Endpoints)
package com.inventario.ms_operador.controller;

import com.inventario.ms_operador.model.Product;
import com.inventario.ms_operador.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> crear(@Valid @RequestBody Product producto) {
        return new ResponseEntity<>(service.guardarProducto(producto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
}