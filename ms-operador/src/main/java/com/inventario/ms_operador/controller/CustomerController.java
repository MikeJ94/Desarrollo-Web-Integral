package com.inventario.ms_operador.controller;

import com.inventario.ms_operador.dto.ShipmentRequestDTO;
import com.inventario.ms_operador.model.Customer;
import com.inventario.ms_operador.model.CustomerShipment;
import com.inventario.ms_operador.model.Product;
import com.inventario.ms_operador.repository.CustomerRepository;
import com.inventario.ms_operador.repository.CustomerShipmentRepository;
import com.inventario.ms_operador.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos") // Seguimos tu estructura de rutas
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final CustomerShipmentRepository shipmentRepository;

    // 1. Obtener todos los clientes (Para el select en React)
    @GetMapping("/clientes")
    public List<Customer> listarClientes() {
        return customerRepository.findAll();
    }

    @GetMapping("/envios")
    public List<CustomerShipment> listarEnvios() {
    return shipmentRepository.findAll();
}

    // 2. Registrar un nuevo envío (Salida de mercancía)
    // El frontend enviará un JSON como: { "idProduct": 1, "idCustomer": 2, "quantity": 5 }
    @PostMapping("/envios")
    public ResponseEntity<?> crearEnvio(@RequestBody ShipmentRequestDTO request) {
    try {
        productService.registrarEnvio(
            request.getIdProduct(), 
            request.getIdCustomer(), 
            request.getQuantity()
        );
        return ResponseEntity.ok(Map.of("message", "Envío registrado exitosamente"));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    // 3. (Opcional) Registrar un nuevo cliente si no existen
    @PostMapping("/clientes")
    public Customer guardarCliente(@RequestBody Customer cliente) {
        return customerRepository.save(cliente);
    }
}