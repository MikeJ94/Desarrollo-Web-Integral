package com.inventario.ms_operador.model;

import lombok.Data;

// DTO que se enviará como evento a través del broker
@Data
public class StockUpdateEvent {
    private Long productId;
    private int newStock;
}