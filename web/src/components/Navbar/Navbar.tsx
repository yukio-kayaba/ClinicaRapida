import { useState } from 'react';
import { RolPersonal } from '../../types/Personal';
import './Navbar.css';

interface NavbarProps {
  searchTerm: string;
  onSearchChange: (value: string) => void;
  selectedRol: RolPersonal | 'todos';
  onRolFilterChange: (rol: RolPersonal | 'todos') => void;
}

export const Navbar = ({ searchTerm, onSearchChange, selectedRol, onRolFilterChange }: NavbarProps) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const scrollToSection = (sectionId: string) => {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
    setIsMenuOpen(false);
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand">
          <svg className="navbar-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
            />
          </svg>
          <div className="navbar-brand-text">
            <span className="navbar-title">Hospital Monje Medrano</span>
            <span className="navbar-subtitle">Centro Médico de Excelencia</span>
          </div>
        </div>

        <button 
          className="navbar-menu-toggle"
          onClick={() => setIsMenuOpen(!isMenuOpen)}
          aria-label="Toggle menu"
        >
          <span></span>
          <span></span>
          <span></span>
        </button>

        <div className={`navbar-menu ${isMenuOpen ? 'navbar-menu-open' : ''}`}>
          <button 
            className="navbar-link"
            onClick={() => scrollToSection('inicio')}
          >
            Inicio
          </button>
          <button 
            className="navbar-link"
            onClick={() => scrollToSection('personal')}
          >
            Nuestro Personal
          </button>
          <button 
            className="navbar-link"
            onClick={() => scrollToSection('servicios')}
          >
            Servicios
          </button>
        </div>
      </div>

      {/* Sección de búsqueda y filtros */}
      <div className="navbar-search-section">
        <div className="navbar-search-container">
          <div className="navbar-search-wrapper">
            <svg className="navbar-search-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              />
            </svg>
            <input
              type="text"
              className="navbar-search-input"
              placeholder="Buscar personal por nombre..."
              value={searchTerm}
              onChange={(e) => onSearchChange(e.target.value)}
            />
            {searchTerm && (
              <button
                className="navbar-search-clear"
                onClick={() => onSearchChange('')}
                aria-label="Limpiar búsqueda"
              >
                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            )}
          </div>

          <div className="navbar-filters">
            <button
              className={`navbar-filter-btn ${selectedRol === 'todos' ? 'active' : ''}`}
              onClick={() => onRolFilterChange('todos')}
            >
              Todos
            </button>
            <button
              className={`navbar-filter-btn ${selectedRol === 'doctor' ? 'active' : ''}`}
              onClick={() => onRolFilterChange('doctor')}
            >
              Médicos
            </button>
            <button
              className={`navbar-filter-btn ${selectedRol === 'enfermero' ? 'active' : ''}`}
              onClick={() => onRolFilterChange('enfermero')}
            >
              Enfermeras
            </button>
            <button
              className={`navbar-filter-btn ${selectedRol === 'obstetra' ? 'active' : ''}`}
              onClick={() => onRolFilterChange('obstetra')}
            >
              Obstetras
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};
