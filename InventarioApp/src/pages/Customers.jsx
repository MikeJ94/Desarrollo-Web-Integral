import React from 'react';
import Layout from '../components/Layout';
import { useInventory } from '../hooks/useInventory';
import ShipmentForm from '../components/ShipmentForm';

const Customers = () => {
  // Extraemos los datos necesarios del hook
  const { products, customers, shipments, executeShipment } = useInventory();

  const handleCustomerOrder = async (productId, customerId, qty) => {
    const success = await executeShipment(productId, customerId, qty);
    if (success) {
      alert("✅ Envío registrado con éxito");
    }
  };

  // --- FUNCIONES HELPER (Deben estar dentro de Customers) ---

  const getProductName = (id) => {
    // Buscamos el producto comparando los posibles nombres de ID
    const product = products.find(p => (p.idProduct || p.id) == id);
    return product ? product.nombre : `Producto #${id}`;
  };

  const getCustomerName = (id) => {
    // Buscamos el cliente comparando los posibles nombres de ID
    const customer = customers.find(c => (c.idCustomer || c.id) == id);
    return customer ? customer.nombre : `Cliente #${id}`;
  };

  return (
    <Layout title="Envíos a Clientes">
      <div className="page-content">
        <section className="order-section">
          <p>Utilice este formulario para despachar productos del inventario.</p>
          <ShipmentForm 
            products={products} 
            customers={customers} 
            onSubmit={handleCustomerOrder} 
          />
        </section>

        <hr style={{ margin: '40px 0', border: '0', borderTop: '1px solid #eee' }} />

        <section className="history-section">
          <h3>Historial de Despachos Recientes</h3>
          <div className="table-container" style={{ marginTop: '20px', overflowX: 'auto' }}>
            <table className="inventory-table">
              <thead>
                <tr>
                  <th>ID Envío</th>
                  <th>Fecha</th>
                  <th>Cliente</th>
                  <th>Producto</th>
                  <th>Cantidad</th>
                </tr>
              </thead>
              <tbody>
                {shipments && shipments.length > 0 ? (
                  // Usamos una copia para no mutar el estado original al hacer el reverse
                  [...shipments].reverse().map((ship) => (
                    <tr key={ship.idShipment || ship.id}>
                      <td>#{ship.idShipment || ship.id}</td>
                      <td>
                        {ship.shipmentDate 
                          ? new Date(ship.shipmentDate).toLocaleString() 
                          : 'Fecha no disponible'}
                      </td>
                      {/* Aquí llamamos a las funciones helper corregidas */}
                      <td>{getCustomerName(ship.idCustomer)}</td>
                      <td>{getProductName(ship.idProduct)}</td>
                      <td style={{ fontWeight: 'bold', color: '#e74c3c' }}>
                        -{ship.quantity}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" style={{ textAlign: 'center', padding: '20px' }}>
                      No hay registros de envíos aún.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </section>
      </div>
    </Layout>
  );
};

export default Customers;