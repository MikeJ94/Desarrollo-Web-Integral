package com.inventario.ms_operador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor // Necesario para JPA y Builder
public class SupplierOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;
    
    @Column(name = "id_product")
    private Long idProduct;
    
    @Column(name = "id_supplier")
    private Long idSupplier; 

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "quantity")
    private int quantity;
    
    
    // Constructor usado por el servicio
    public SupplierOrder(Long idProduct, Long idSupplier, int quantity) {
        this.idProduct = idProduct;
        this.idSupplier = idSupplier;
        this.orderDate = LocalDateTime.now(); // Se asigna la fecha al momento de crear
        this.quantity = quantity;

    }
    // Método que se ejecuta antes de insertar en la BD
    @PrePersist
    protected void onCreate() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();
        }
    }
}