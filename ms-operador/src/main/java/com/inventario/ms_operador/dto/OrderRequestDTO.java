package com.inventario.ms_operador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @JsonProperty("idProduct")
    private Long idProduct;

    @JsonProperty("idSupplier")
    private Long idSupplier;

    @JsonProperty("quantity")
    private int quantity;
}