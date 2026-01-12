/* package com.inventario.ms_buscador.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.*;

@Document(indexName = "products") // Nombre del índice en Elasticsearch
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id; // En Elastic es recomendable usar String para el ID

    @Field(type = FieldType.Long)
    private Long idSupplier;

    // Usamos 'nombre' para que coincida con tu JSON del operador
    // El analyzer "search_as_you_type" es para sugerencias instantáneas
    @Field(type = FieldType.Text, analyzer = "standard")
    private String nombre;

    // IMPORTANTE: Keyword permite hacer agregaciones (Facets)
    @Field(type = FieldType.Keyword)
    private String categoria;

    @Field(type = FieldType.Integer)
    private Integer stock;
} */


package com.inventario.ms_buscador.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "productos")
public class Product {
    @Id
    private String id;
    private Long idSupplier;
    private String nombre;
    private String categoria;
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

    // Simulación de Builder manual para que ProductConsumer compile
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