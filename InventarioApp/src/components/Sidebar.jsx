import React from 'react';
import { NavLink } from 'react-router-dom';
import '../App.css';

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <div className="sidebar__logo"> Inventario</div>
      <nav className="sidebar__nav">
        {}
        <NavLink to="/" className={({ isActive }) => `sidebar__link ${isActive ? 'sidebar__link--active' : ''}`}>Dashboard</NavLink>
        <NavLink to="/inventory" className={({ isActive }) => `sidebar__link ${isActive ? 'sidebar__link--active' : ''}`}>Inventario</NavLink>
        <NavLink to="/suppliers" className={({ isActive }) => `sidebar__link ${isActive ? 'sidebar__link--active' : ''}`}>Proveedores (Entradas)</NavLink>
        <NavLink to="/customers" className={({ isActive }) => `sidebar__link ${isActive ? 'sidebar__link--active' : ''}`}>Clientes (Salidas)</NavLink>
      </nav>
    </aside>
  );
};

export default Sidebar;