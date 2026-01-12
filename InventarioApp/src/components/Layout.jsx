import React from 'react';
import Sidebar from './Sidebar';
import Header from './Header';

const Layout = ({ children, title }) => {
  return (
    <div className="layout">
      <Sidebar />
      <main className="layout__content">
        <Header title={title} />
        <div className="layout__body">
          {children}
        </div>
      </main>
    </div>
  );
};

export default Layout;