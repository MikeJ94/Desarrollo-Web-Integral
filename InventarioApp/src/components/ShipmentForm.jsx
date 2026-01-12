import React, { useState } from 'react';

const ShipmentForm = ({ products = [], customers = [], onSubmit }) => {
  const [selectedProductId, setSelectedProductId] = useState('');
  const [selectedCustomerId, setSelectedCustomerId] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [isSubmitting, setIsSubmitting] = useState(false); // Para evitar doble clic

  const handleCancel = () => {
    setSelectedProductId('');
    setSelectedCustomerId('');
    setQuantity(1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // if (selectedProductId && selectedCustomerId && quantity > 0) {
    //   onSubmit(selectedProductId, selectedCustomerId, parseInt(quantity));
    //   setSelectedProductId('');
    //   setSelectedCustomerId('');
    //   setQuantity(1);
    // }
    
    if (isSubmitting) return;
    // Buscamos el producto para validar stock localmente antes de enviar
    const product = products.find(p => String(p.idProduct || p.id) === String(selectedProductId));
    
    if (product && parseInt(quantity) > product.stock) {
      alert(`Error: No hay suficiente stock. Disponible: ${product.stock}`);
      return;
    }

    if (selectedProductId && selectedCustomerId && quantity > 0) {
      setIsSubmitting(true);
      try {
        await onSubmit(selectedProductId, selectedCustomerId, parseInt(quantity));
        // Reset campos
        setSelectedProductId('');
        setSelectedCustomerId('');
        setQuantity(1);
      } finally {
        setIsSubmitting(false);
      }
    }
  };

  return (
    <form className="order-form" onSubmit={handleSubmit}>
      <h3 className="order-form__title">Nuevo Envío a Cliente</h3>
      
      <div className="order-form__group">
        <label className="order-form__label">Producto</label>
        <select 
          className="order-form__select" 
          value={selectedProductId} 
          onChange={(e) => setSelectedProductId(e.target.value)} 
          required
        >
          <option value="">Seleccione un producto</option>
          {products?.map(p => (
            <option key={p.idProduct || p.id} value={p.idProduct || p.id}>
              {p.nombre} (Disponible: {p.stock})
            </option>
          ))}
        </select>
      </div>

      <div className="order-form__group">
        <label className="order-form__label">Cliente</label>
        <select 
          className="order-form__select" 
          value={selectedCustomerId} 
          onChange={(e) => setSelectedCustomerId(e.target.value)} 
          required
        >
          <option value="">Seleccione el cliente</option>
          {customers?.map(c => (
            // Usamos c.id porque es lo que viene en tu JSON de Donald Trump
            <option key={c.id || c.idCustomer} value={c.id || c.idCustomer}>
              {c.nombre}
            </option>
          ))}
        </select>
      </div>

      <div className="order-form__group">
        <label className="order-form__label">Cantidad a Enviar</label>
        <input 
          type="number" 
          className="order-form__input" 
          min="1" 
          value={quantity} 
          onChange={(e) => setQuantity(e.target.value)} 
          required
        />
      </div>

      <div className="order-form__actions" style={{ display: 'flex', gap: '10px' }}>
        <button type="submit" className="order-form__button" style={{ backgroundColor: '#e67e22', color: 'white' }}>
          
          {isSubmitting ? 'Procesando...' : 'Registrar Salida'}
        </button>
        <button type="button" className="order-form__button" onClick={handleCancel} style={{ backgroundColor: '#95a5a6', color: 'white' }}>
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default ShipmentForm;