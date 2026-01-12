import React, { useState, useEffect } from 'react';
import Layout from '../components/Layout';
import SearchBar from '../components/SearchBar';
import ProductCard from '../components/ProductCard';
import { useInventory } from '../hooks/useInventory';

const Inventory = () => {
  const { products, suppliers, loading, searchProducts } = useInventory();
  
  const [filtered, setFiltered] = useState([]);

  const [searchTerm, setSearchTerm] = useState(""); // Nuevo estado local para el término

  const [isSearching, setIsSearching] = useState(false); // Nuevo estado local

  // Sincronizar lista inicial
  useEffect(() => {
    setFiltered(products);
  }, [products]);

  // LÓGICA DE DEBOUNCE
  useEffect(() => {
    // Si no hay término, volvemos a la lista original sin parpadeos
    if (searchTerm.trim() === "") {
      setFiltered(products);
      return;
    }

    const delayDebounceFn = setTimeout(async () => {
      setIsSearching(true); // Solo indicamos que buscamos, no bloqueamos la UI
      const results = await searchProducts(searchTerm);
      setFiltered(results);
      setIsSearching(false);
    }, 400);

    return () => clearTimeout(delayDebounceFn);
  }, [searchTerm, products, searchProducts]);

  // Esta función ahora solo actualiza el estado local, no hace la petición
  const handleSearch = (term) => {
    setSearchTerm(term);
  };


  /* const handleSearch = async (term) => {
    const results = await searchProducts(term);
    setFiltered(results);
  }; */

  return (
    <Layout title="Inventario y Búsqueda">
      <SearchBar onSearch={handleSearch} />
      
      {/* CAMBIO CLAVE: Solo mostramos "Cargando..." en la carga inicial (loading).
        Durante la búsqueda (isSearching), no ocultamos los productos.
      */}
      {loading && products.length === 0 ? (
        <p className="loading-text">Cargando inventario...</p>
      ) : (
        <div className="inventory-grid">
          {filtered.map(p => (
            <ProductCard 
            key={p.id} 
            product={p} 
            suppliers={suppliers || []} // Aseguramos que siempre sea al menos un array vacío
            />
          ))}
          {filtered.length === 0 && <p>No se encontraron productos.</p>}
        </div>
      )}
    </Layout>
  );
};

export default Inventory;