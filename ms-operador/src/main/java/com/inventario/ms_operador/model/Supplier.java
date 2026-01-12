package com.inventario.ms_operador.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "suppliers") // Nombre exacto de tu tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Útil para crear proveedores en los tests
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier") // Mapeo al nombre manual
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "name")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "address")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "phone")
    private String telefono;
}
