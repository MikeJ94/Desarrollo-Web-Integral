package com.inventario.ms_buscador.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// Quitamos el 'createIndex = false' de aquí, eso se maneja en el Repository o Config
@Document(indexName = "productos") 
public class Product {
    @Id
    private String id;
    
    @Field(type = FieldType.Long)
    private Long idSupplier;
    
    @Field(type = FieldType.Text)
    private String nombre;
    
    @Field(type = FieldType.Keyword)
    private String categoria;
    
    @Field(type = FieldType.Integer)
    private Integer stock;

    public Product() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getIdSupplier() { return idSupplier; }
    public void setIdSupplier(Long idSupplier) { this.idSupplier = idSupplier; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    // Builder manual para compatibilidad con el Consumer
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Product p = new Product();
        public ProductBuilder id(String id) { p.setId(id); return this; }
        public ProductBuilder idSupplier(Long id) { p.setIdSupplier(id); return this; }
        public ProductBuilder nombre(String n) { p.setNombre(n); return this; }
        public ProductBuilder categoria(String c) { p.setCategoria(c); return this; }
        public ProductBuilder stock(Integer s) { p.setStock(s); return this; }
        public Product build() { return p; }
    }
}