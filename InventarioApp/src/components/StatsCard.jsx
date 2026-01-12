import React from 'react';

const StatsCard = ({ label, value }) => (
  <div className="stats-card">
    <div className="stats-card__value">{value}</div>
    <div className="stats-card__label">{label}</div>
  </div>
);

export default StatsCard;