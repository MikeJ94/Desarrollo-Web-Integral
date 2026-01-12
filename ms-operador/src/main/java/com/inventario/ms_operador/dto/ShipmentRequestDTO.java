package com.inventario.ms_operador.dto;

import lombok.Data;

@Data
public class ShipmentRequestDTO {
    private Long idProduct;
    private Long idCustomer;
    private Integer quantity;
}