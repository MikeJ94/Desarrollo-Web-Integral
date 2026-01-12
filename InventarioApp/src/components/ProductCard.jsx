import React from 'react';

const ProductCard = ({ product, suppliers = [] }) => {
  // Verificamos si el stock es bajo para aplicar la clase CSS correspondiente
  const stockClass = product.stock < 10 ? 'product-card__stock--low' : 'product-card__stock--ok';

  // Buscamos el proveedor en la lista global usando el idSupplier que trae el producto
  const prov= suppliers.find(s => Number(s.id) === Number(product.idSupplier));
  
  // Si lo encuentra, muestra el nombre; si no, muestra el ID o un mensaje
 const nombreProveedor = prov ? (prov.nombre || prov.name) : `ID: ${product.idSupplier}`;

  return (
    <div className="product-card">
      {/* Cambiado de .name a .nombre */}
      <h3 className="product-card__title">{product.nombre}</h3>
      
      <p className="product-card__info">
        <strong>Categoría:</strong> {product.categoria}
      </p>
      
      <p className="product-card__info">
        <strong>Proveedor:</strong> {nombreProveedor}
      </p>
      
      <div className={`product-card__stock ${stockClass}`}>
        Stock: {product.stock}
      </div>
    </div>
  );
};

export default ProductCard;