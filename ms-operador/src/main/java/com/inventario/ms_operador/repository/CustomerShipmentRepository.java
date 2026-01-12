package com.inventario.ms_operador.repository;

import com.inventario.ms_operador.model.CustomerShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerShipmentRepository extends JpaRepository<CustomerShipment, Long> {
    
}
