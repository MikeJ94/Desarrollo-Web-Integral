/* package com.inventario.ms_operador.repository;

import com.inventario.ms_operador.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
} */

//B. El Repositorio
package com.inventario.ms_operador.repository;

import com.inventario.ms_operador.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}