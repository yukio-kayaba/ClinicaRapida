import { useState, useMemo } from 'react';
import { usePersonal } from '../../context/PersonalContext';
import { PersonalCard } from './PersonalCard';
import { PersonalModal } from './PersonalModal';
import { Personal, RolPersonal } from '../../types/Personal';
import './ViewPersonal.css';

interface ViewPersonalProps {
  searchTerm: string;
  selectedRol: RolPersonal | 'todos';
}

export const ViewPersonal = ({ searchTerm, selectedRol }: ViewPersonalProps) => {
  const { personal, loading } = usePersonal();
  const [selectedPersonal, setSelectedPersonal] = useState<Personal | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCardClick = (persona: Personal) => {
    setSelectedPersonal(persona);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedPersonal(null);
  };

  // Filtrar personal basado en búsqueda y rol
  const filteredPersonal = useMemo(() => {
    let filtered = personal;

    // Filtrar por rol
    if (selectedRol !== 'todos') {
      filtered = filtered.filter((persona) => persona.rol === selectedRol);
    }

    // Filtrar por término de búsqueda
    if (searchTerm.trim()) {
      const term = searchTerm.toLowerCase().trim();
      filtered = filtered.filter((persona) => {
        const nombreMatch = persona.nombre.toLowerCase().includes(term);
        const especialidadMatch = persona.especialidad.toLowerCase().includes(term);
        const descripcionMatch = persona.descripcion.toLowerCase().includes(term);
        
        return nombreMatch || especialidadMatch || descripcionMatch;
      });
    }

    return filtered;
  }, [personal, searchTerm, selectedRol]);

  if (loading) {
    return (
      <div className="view-personal-loading">
        <div className="view-personal-loading-content">
          <div className="view-personal-spinner"></div>
          <p className="view-personal-loading-text">Cargando personal médico...</p>
        </div>
      </div>
    );
  }

  if (!loading && personal.length === 0) {
    return (
      <div className="view-personal-empty">
        <div className="view-personal-empty-content">
          <svg
            className="view-personal-empty-icon"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
            />
          </svg>
          <p className="view-personal-empty-title">No hay personal médico registrado</p>
          <p className="view-personal-empty-subtitle">
            Agrega personal médico para comenzar
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="view-personal">
      <div className="view-personal-container">
        {/* Sección de bienvenida */}
        <section id="inicio" className="view-personal-welcome">
          <h1 className="view-personal-welcome-title">
            Bienvenido al Hospital Monje Medrano
          </h1>
          <p className="view-personal-welcome-text">
            Nos enorgullece contar con un equipo médico altamente calificado y comprometido 
            con la excelencia en la atención de nuestros pacientes.
          </p>
        </section>

        {/* Sección de personal */}
        <section id="personal" className="view-personal-section">
          <div className="view-personal-header">
            <h2 className="view-personal-title">
              Nuestro Personal
            </h2>
            <p className="view-personal-subtitle">
              {filteredPersonal.length} {filteredPersonal.length === 1 ? 'resultado' : 'resultados'} 
              {searchTerm || selectedRol !== 'todos' ? ' encontrado' : ''}
              {!searchTerm && selectedRol === 'todos' && ` de ${personal.length} ${personal.length === 1 ? 'miembro' : 'miembros'} del equipo médico`}
            </p>
          </div>

          {filteredPersonal.length === 0 ? (
            <div className="view-personal-no-results">
              <svg
                className="view-personal-empty-icon"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                />
              </svg>
              <p className="view-personal-empty-title">No se encontraron resultados</p>
              <p className="view-personal-empty-subtitle">
                Intenta con otros términos de búsqueda o filtros
              </p>
            </div>
          ) : (
            <div className="view-personal-grid">
              {filteredPersonal.map((persona) => (
                <PersonalCard 
                  key={persona.id} 
                  personal={persona}
                  onClick={() => handleCardClick(persona)}
                />
              ))}
            </div>
          )}
        </section>

        {/* Modal */}
        <PersonalModal
          personal={selectedPersonal}
          isOpen={isModalOpen}
          onClose={handleCloseModal}
        />

        {/* Sección de servicios */}
        <section id="servicios" className="view-personal-section">
          <div className="view-personal-header">
            <h2 className="view-personal-title">Nuestros Servicios</h2>
            <p className="view-personal-subtitle">
              Ofrecemos una amplia gama de servicios médicos especializados
            </p>
          </div>
          <div className="view-personal-services">
            <div className="view-personal-service-card">
              <h3>Cardiología</h3>
              <p>Atención especializada en enfermedades cardiovasculares</p>
            </div>
            <div className="view-personal-service-card">
              <h3>Pediatría</h3>
              <p>Cuidados médicos especializados para niños y adolescentes</p>
            </div>
            <div className="view-personal-service-card">
              <h3>Ginecología y Obstetricia</h3>
              <p>Atención integral para la salud de la mujer</p>
            </div>
            <div className="view-personal-service-card">
              <h3>Neurología</h3>
              <p>Diagnóstico y tratamiento de enfermedades neurológicas</p>
            </div>
            <div className="view-personal-service-card">
              <h3>Cuidados Intensivos</h3>
              <p>Unidad de cuidados intensivos con tecnología de vanguardia</p>
            </div>
            <div className="view-personal-service-card">
              <h3>Emergencias</h3>
              <p>Atención de emergencias médicas 24/7</p>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};

