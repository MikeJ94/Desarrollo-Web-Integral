import { useState, useEffect, useCallback } from 'react';
// import { initialProducts } from '../data/mockData';
import { inventoryService, searchService } from '../api/inventoryApi';

export const useInventory = () => {
  const [products, setProducts] = useState([]);
  const [suppliers, setSuppliers] = useState([]); // Estado para proveedores
  const [customers, setCustomers] = useState([]); // Nuevo: Estado para clientes
  const [orders, setOrders] = useState([]); // Nuevo estado para órdenes
  const [shipments, setShipments] = useState([]); // NUEVO: Historial de envíos
  const [loading, setLoading] = useState(true);
  

  /* const searchProducts = (term) => {
    if (!term) return initialProducts;
    return initialProducts.filter(p => 
      p.name.toLowerCase().includes(term.toLowerCase()) ||
      p.category.toLowerCase().includes(term.toLowerCase()) ||
      p.supplier.toLowerCase().includes(term.toLowerCase())
    );
  };

  const updateStock = (id, quantity, type) => {
    setProducts(prev => prev.map(p => {
      if (p.id === parseInt(id)) {
        return {
          ...p,
          stock: type === 'add' ? p.stock + quantity : p.stock - quantity
        };
      }
      return p;
    }));
  };

  return { products, loading, searchProducts, updateStock, setProducts }; */

  // Función para cargar productos del backend
  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      const [prodRes, suppRes,orderRes,custRes,shipRes] = await Promise.all([
        inventoryService.getProducts(),
        inventoryService.getSuppliers(),
        inventoryService.getOrders(), // Asegúrate de tener este método en inventoryApi.js
        inventoryService.getCustomers(), // Debes implementar este en inventoryApi.js
        inventoryService.getShipments() // NUEVO
      ]);
      setProducts(prodRes.data);
      setSuppliers(suppRes.data);
      setOrders(orderRes.data); // Guardamos las órdenes en el estado
      setCustomers(custRes.data); // Guardamos clientes
      setShipments(shipRes.data); // Guardamos los envíos
    } catch (error) {
      console.error("Error cargando datos:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchData(); }, [fetchData]);

  // Para Compras a Proveedores
  // Función para actualizar stock llamando al backend
  const executeOrder = async (productId, supplierId, quantity) => {
    try {
      // Pasamos los tres parámetros al servicio // 1. Enviamos la orden al ms-operador
      await inventoryService.createOrder(productId, supplierId, quantity);
      // 2. IMPORTANTE: Recargamos todo para actualizar Stock y Tabla de Órdenes
      await fetchData(); // Recarga los datos para ver el nuevo stock (B5)
      return true;
    } catch (error) {
      console.error("Error al ejecutar orden:", error);
      //alert("No se pudo completar la operación");
      return false;
    }
  };

  // NUEVO: Para Envíos a Clientes
  const executeShipment = async (productId, customerId, quantity) => {
    try {
      await inventoryService.createShipment(productId, customerId, quantity);
      await fetchData(); // Refresca stock y listas
      return true;
    } catch (error) {
      const msg = error.response?.data || "Error al procesar envío";
      alert(msg); // Aquí mostrará "Stock insuficiente"
      return false;
    }
  };

  // 2. Añade esta función antes del return
  const searchProducts = async (term) => {
    if (!term.trim()) {
      return products; // Si no hay término, devolvemos la lista original (PostgreSQL)
    }

    try {
      setLoading(true);
      const response = await searchService.search(term);
      return response.data; // Los resultados que vienen de Elasticsearch
    } catch (error) {
      console.error("Error buscando en Elasticsearch:", error);
      return [];
    } finally {
      setLoading(false);
    }
  };

  return { products, suppliers, orders, customers,shipments, loading, executeOrder,executeShipment,searchProducts,refreshData: fetchData };
};