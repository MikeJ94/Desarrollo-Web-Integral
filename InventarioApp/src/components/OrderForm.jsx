import React, { useState } from 'react';

// Formulario genérico para Pedidos (Proveedores) o Envíos (Clientes)
const OrderForm = ({ type, products = [], suppliers = [], onSubmit }) => {
  const [selectedProductId, setSelectedProductId] = useState('');
  const [selectedSupplierId, setSelectedSupplierId] = useState('');
  const [quantity, setQuantity] = useState(1);

  // Función para resetear el formulario
  const handleCancel = () => {
    setSelectedProductId('');
    setSelectedSupplierId('');
    setQuantity(1);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // LOG DE CONTROL: Verifica esto en tu consola F12
    console.log("Datos capturados:", { selectedProductId, selectedSupplierId, quantity });

    if (selectedProductId && selectedSupplierId && quantity > 0) {
      // 1. Enviamos los datos
      onSubmit(selectedProductId, selectedSupplierId, parseInt(quantity));

      // 2. Mensaje de éxito
      alert('Operación enviada con éxito');
      
      // 3. LIMPIEZA DE CAMPOS (Reseteamos a los valores iniciales)
      setSelectedProductId('');
      setSelectedSupplierId('');
      setQuantity(1); // Volvemos a la cantidad mínima por defecto
      handleCancel();
    }else {
      alert('Por favor complete todos los campos');
    }
  };

  return (
   <form className="order-form" onSubmit={handleSubmit}>
      <h3 className="order-form__title">
        {type === 'in' ? 'Nueva Solicitud a Proveedor' : 'Nuevo Envío a Cliente'}
      </h3>
      
      {/* Selector de Producto */}
      <div className="order-form__group">
        <label className="order-form__label">Producto</label>
        <select className="order-form__select" value={selectedProductId} onChange={(e) => setSelectedProductId(e.target.value)} required>
          <option value="">Seleccione un producto</option>
          {products?.map(p => (
            <option key={p.idProduct || p.id} value={p.idProduct || p.id}>
              {p.nombre} (Stock: {p.stock})
              </option>
          ))}
        </select>
      </div>

      {/* Selector de Proveedor (NUEVO) */}
      <div className="order-form__group">
        <label className="order-form__label">Proveedor</label>
        <select className="order-form__select" value={selectedSupplierId} onChange={(e) => setSelectedSupplierId(e.target.value)} required>
          <option value="">Seleccione el proveedor</option>
          {suppliers?.map(s => (
            <option key={s.idSupplier || s.id} value={s.idSupplier || s.id}>{s.nombre}</option>
          ))}
        </select>
      </div>

      <div className="order-form__group">
        <label className="order-form__label">Cantidad</label>
        <input 
        type="number" 
        className="order-form__input" 
        min="1" 
        value={quantity} 
        onChange={(e) => setQuantity(e.target.value)} 
        required
        />
      </div>

      {/* Contenedor de botones */}
      <div className="order-form__actions" style={{ display: 'flex', gap: '10px' }}>
        <button type="submit" className={`order-form__button order-form__button--${type}`}>
          Enviar Solicitud
        </button>
        
        <button 
          type="button" 
          className="order-form__button order-form__button--cancel" 
          onClick={handleCancel}
          style={{ backgroundColor: '#95a5a6', color: 'white' }}
        >
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default OrderForm;