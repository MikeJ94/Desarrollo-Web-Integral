package com.inventario.ms_buscador.controller;

import com.inventario.ms_buscador.model.Product;
import com.inventario.ms_buscador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/buscar")
@CrossOrigin(origins = "*") // Para evitar problemas de CORS durante pruebas
public class ProductController {

    @Autowired
    private ProductRepository repository;

    // 1. Ver todos los productos indexados
    @GetMapping("/productos")
    public List<Product> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    // 2. Búsqueda por nombre (Criterio 1: Full-text Search)
    @GetMapping("/nombre/{query}")
    public List<Product> searchByName(@PathVariable String query) {
        return repository.findByNombreContaining(query);
    }

    // 3. Facets: Filtrar por categoría (Criterio 3)
    @GetMapping("/categoria/{cat}")
    public List<Product> searchByCategory(@PathVariable String cat) {
        return repository.findByCategoria(cat);
    }

    // NUEVO: Método de búsqueda unificado para el Frontend
    // Endpoint: GET /api/buscar/search?query=logitech
    @GetMapping("/search")
    public List<Product> search(@RequestParam(name = "query", required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            return StreamSupport.stream(repository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
        }
        // Usamos el método que ya tienes en el repository
        return repository.findByNombreContainingIgnoreCaseOrCategoriaContainingIgnoreCase(query, query);
    }

}