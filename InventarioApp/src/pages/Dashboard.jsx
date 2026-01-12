import React from 'react';
import Layout from '../components/Layout';
import StatsCard from '../components/StatsCard';
import { useInventory } from '../hooks/useInventory';

const Dashboard = () => {
  const { products, loading } = useInventory();

  if (loading) return <p>Cargando estadísticas...</p>;
  // Cálculos simples para el dashboard
  const totalItems = products.reduce((acc, curr) => acc + curr.stock, 0);
  const lowStockItems = products.filter(p => p.stock < 10).length;

  return (
    <Layout title="Resumen General">
      <div className="dashboard-grid">
        <StatsCard label="Tipos de Productos" value={products.length} />
        <StatsCard label="Items Totales en Almacén" value={totalItems} />
        <StatsCard label="Alertas Stock Bajo" value={lowStockItems} />
      </div>
    </Layout>
  );
};

export default Dashboard;