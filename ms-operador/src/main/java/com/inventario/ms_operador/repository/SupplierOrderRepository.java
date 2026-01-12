package com.inventario.ms_operador.repository;

import com.inventario.ms_operador.model.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
    
}