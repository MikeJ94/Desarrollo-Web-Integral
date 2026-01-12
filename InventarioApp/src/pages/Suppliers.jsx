import React from 'react';
import Layout from '../components/Layout';
import OrderForm from '../components/OrderForm';
import { useInventory } from '../hooks/useInventory';

const Suppliers = () => {
  const { products, suppliers,orders, executeOrder } = useInventory();

 /*  const handleSupplierOrder = (id, qty) => {
    updateStock(id, qty, 'add');
    // Aquí podrías agregar lógica para guardar en un historial de pedidos
  }; */

  const handleSupplierOrder = async (productId, supplierId, quantity) => {
    // Ahora capturamos los 3 datos que envía el OrderForm
    console.log("Datos recibidos en Suppliers:", { productId, supplierId, quantity });
    // Pasamos los 3 datos al hook/servicio
    const success = await executeOrder(productId, supplierId, quantity);
    if (success) {
      // El alert ya está en el componente OrderForm, pero aquí confirmamos la lógica
    }
  };

  // Función para obtener el nombre del producto según su ID
  const getProductName = (id) => {
    const product = products.find(p => (p.idProduct || p.id) == id);
    return product ? product.nombre : `Producto #${id}`;
  };

  // Función para obtener el nombre del proveedor según su ID
  const getSupplierName = (id) => {
    const supplier = suppliers.find(s => (s.idSupplier || s.id) == id);
    return supplier ? supplier.nombre : `Proveedor #${id}`;
  };

  return (
    <Layout title="Pedidos a Proveedores">
      <div className="page-content">
        <section className="order-section">
          <p>Utilice este formulario para solicitar reabastecimiento de stock externo.</p>
          <OrderForm 
            type="in" 
            products={products} 
            suppliers={suppliers} 
            onSubmit={handleSupplierOrder} 
          />
        </section>

        <hr style={{ margin: '40px 0', border: '0', borderTop: '1px solid #eee' }} />

        {/* 2. Nueva sección de Historial */}
        <section className="history-section">
          <h3>Historial de Pedidos Recientes</h3>
          <div className="table-container" style={{ marginTop: '20px', overflowX: 'auto' }}>
            <table className="inventory-table">
              <thead>
                <tr>
                  <th>ID Orden</th>
                  <th>Fecha</th>
                  <th>Proveedor</th>
                  <th>Producto</th>
                  <th>Cantidad</th>
                </tr>
              </thead>
              <tbody>
                {orders && orders.length > 0 ? (
                  // Ordenamos por fecha descendente (la más nueva arriba)
                  [...orders].reverse().map((order) => (
                    <tr key={order.idOrder}>
                      <td>#{order.idOrder}</td>
                      <td>{new Date(order.orderDate).toLocaleString()}</td>
                      <td>{getSupplierName(order.idSupplier)}</td>
                      <td>{getProductName(order.idProduct)}</td>
                      <td style={{ fontWeight: 'bold', color: '#2ecc71' }}>
                        +{order.quantity}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" style={{ textAlign: 'center', padding: '20px' }}>
                      No hay órdenes registradas aún.
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

export default Suppliers;