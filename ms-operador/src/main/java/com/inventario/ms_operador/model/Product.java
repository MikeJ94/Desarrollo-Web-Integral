// package com.inventario.ms_operador.model;

// import jakarta.persistence.*;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

// @Entity
// @Table(name = "products")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Product {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id_product")
//     private Long idProduct;

//     private String name;
//     private String category;
//     private int stock;
    
//     @Column(name = "id_supplier")
//     private Long idSupplier; 

//     public void increaseStock(int quantity) {
//         this.stock += quantity;
//     }
    
//     public void decreaseStock(int quantity) {
//         this.stock -= quantity;
//     }
// }


//A. La Entidad (Persistencia)
/* package com.inventario.ms_operador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @NotNull
    private Integer stock;

    @Positive(message = "El precio debe ser mayor a cero")
    @NotNull
    private Double precio;
} */


package com.inventario.ms_operador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "products") // Nombre exacto de tu tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product") // Mapeo al nombre manual
    private Long id;

    @Column(name = "id_supplier") // Mapeo al nombre manual
    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long idSupplier;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "name")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(name = "category")
    private String categoria;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @NotNull
    @Column(name = "stock")
    private Integer stock;
}