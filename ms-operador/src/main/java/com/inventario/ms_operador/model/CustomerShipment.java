package com.inventario.ms_operador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor // Necesario para JPA y Builder
public class CustomerShipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shipment")
    private Long idShipment;
    
    @Column(name = "id_product")
    private Long idProduct;
    
    @Column(name = "id_customer")
    private Long idCustomer; 

    @Column(name = "shipment_date")
    private LocalDateTime shipmentDate = LocalDateTime.now();

    @Column(name = "quantity")
    private int quantity;
    
    
    // Constructor usado por el servicio
    public CustomerShipment(Long idProduct, Long idCustomer, int quantity) {
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
        this.shipmentDate = LocalDateTime.now(); // Se asigna la fecha al momento de crear
        this.quantity = quantity;
    }
    // Método que se ejecuta antes de insertar en la BD
    @PrePersist
    protected void onCreate() {
        if (this.shipmentDate == null) {
            this.shipmentDate = LocalDateTime.now();
        }
    }
}
