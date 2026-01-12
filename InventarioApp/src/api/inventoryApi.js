import axios from 'axios';

//Local
//const API_BASE_URL = "http://localhost:8082/api";

// Después (Formato Vite):
const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8082";

const inventoryApi = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

export const inventoryService = {
    // --- PRODUCTOS ---
    getProducts: () => inventoryApi.get('/productos'),

    // --- PROVEEDORES ---
    getSuppliers: () => inventoryApi.get('/suppliers'),

    // --- ÓRDENES (COMPRAS) ---
    getOrders: () => inventoryApi.get('/ordenes'),
    
    // POST: Crear orden (Ajustado para recibir id y qty por separado) 
    // Enviamos los nombres exactos del DTO de Java
    createOrder: (productId, supplierId, quantity) => {
        return inventoryApi.post('/ordenes', {
            idProduct: parseInt(productId), // Nombre exacto que espera tu DTO en Java
            idSupplier: parseInt(supplierId),
            quantity: parseInt(quantity)
        });
    },

    // --- CLIENTES Y ENVÍOS (NUEVO) ---
    // Ruta coincide con @GetMapping("/clientes") en CustomerController
    getCustomers: () => inventoryApi.get('/productos/clientes'),
    
    createShipment: (productId, customerId, quantity) => {
        return inventoryApi.post('/productos/envios', {
            idProduct: parseInt(productId),  // Aseguramos que sea número para el DTO Java
            idCustomer: parseInt(customerId),
            quantity: parseInt(quantity)
        });
    },

    // Dentro de inventoryService en inventoryApi.js
    getShipments: () => inventoryApi.get('/productos/envios'),
};


// --- CONFIGURACIÓN DEL BUSCADOR (MS-BUSCADOR) ---
const BUSCADOR_URL = "http://localhost:8083/api/buscar";

const buscadorApi = axios.create({
    baseURL: BUSCADOR_URL,
    headers: { 'Content-Type': 'application/json' }
});

export const searchService = {
    // GET: Buscar en Elasticsearch
    search: (term) => buscadorApi.get(`/search?query=${term}`)
};

export default inventoryApi;