/* package com.inventario.ms_buscador.dto;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;          // Coincide con id_product
    private Long idSupplier;  // Coincide con id_supplier
    private String nombre;    // Coincide con name
    private String categoria; // Coincide con category
    private Integer stock;    // Coincide con stock
}
 */


package com.inventario.ms_buscador.dto;

public class ProductDTO {
    private String id;
    private Long idSupplier;
    private String nombre;
    private String categoria;
    private Integer stock;

    public ProductDTO() {}

    // Getters y Setters manuales
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getIdSupplier() { return idSupplier; }
    public void setIdSupplier(Long idSupplier) { this.idSupplier = idSupplier; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
