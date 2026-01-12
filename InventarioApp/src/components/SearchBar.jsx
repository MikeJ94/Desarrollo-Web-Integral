import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
  const [term, setTerm] = useState('');

  const handleChange = (e) => {
    setTerm(e.target.value);
    onSearch(e.target.value);
  };

  return (
    <div className="search-bar">
      <input 
        type="text" 
        className="search-bar__input" 
        placeholder="Buscar por nombre o categoría..." 
        value={term}
        onChange={handleChange}
      />
    </div>
  );
};

export default SearchBar;