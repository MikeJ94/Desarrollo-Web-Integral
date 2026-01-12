package com.inventario.ms_buscador.repository;

import com.inventario.ms_buscador.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    // Esto permite filtrar por categoría exacta (Facet)
    List<Product> findByCategoria(String categoria);
    
    // Esto permite buscar por nombre (Búsqueda Full-text)
    List<Product> findByNombreContaining(String nombre);

    // Busca en nombre O categoría ignorando mayúsculas/minúsculas
    List<Product> findByNombreContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String nombre, String categoria);
}

    