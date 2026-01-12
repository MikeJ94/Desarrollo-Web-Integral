import React from 'react';
import '../App.css';

const Header = ({ title }) => {
  return (
    <header className="header">
      <h1 className="header__title">{title}</h1>
      <div className="header__user">Admin</div>
    </header>
  );
};

export default Header;