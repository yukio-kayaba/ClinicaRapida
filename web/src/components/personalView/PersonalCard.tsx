import { Personal } from '../../types/Personal';
import './PersonalCard.css';

interface PersonalCardProps {
  personal: Personal;
  onClick: () => void;
}

const getRolLabel = (rol: Personal['rol']): string => {
  switch (rol) {
    case 'doctor':
      return 'Doctor';
    case 'enfermero':
      return 'Enfermero';
    case 'obstetra':
      return 'Obstetra';
    default:
      return rol;
  }
};

const getInitials = (nombre: string): string => {
  return nombre
    .split(' ')
    .map((n) => n[0])
    .join('')
    .toUpperCase()
    .slice(0, 2);
};

export const PersonalCard = ({ personal, onClick }: PersonalCardProps) => {
  const { nombre, rol, especialidad, descripcion, aniosExperiencia, foto } = personal;

  return (
    <div className="personal-card" onClick={onClick}>
      <div className="personal-card-content">
        {/* Foto con efecto 3D */}
        <div className="personal-card-photo-container">
          {foto ? (
            <div className="personal-card-photo-wrapper">
              <img 
                src={foto} 
                alt={nombre}
                className="personal-card-photo"
                onError={(e) => {
                  // Si la imagen falla, mostrar avatar con iniciales
                  const target = e.target as HTMLImageElement;
                  target.style.display = 'none';
                  const parent = target.parentElement;
                  if (parent) {
                    const fallback = parent.querySelector('.personal-card-photo-fallback');
                    if (fallback) {
                      (fallback as HTMLElement).style.display = 'flex';
                    }
                  }
                }}
              />
              <div className="personal-card-photo-fallback">
                {getInitials(nombre)}
              </div>
            </div>
          ) : (
            <div className="personal-card-avatar">
              {getInitials(nombre)}
            </div>
          )}
        </div>

        {/* Contenido */}
        <div className="personal-card-body">
          <div className="personal-card-header">
            <h3 className="personal-card-name">
              {nombre}
            </h3>
            <span className={`personal-card-rol ${rol}`}>
              {getRolLabel(rol)}
            </span>
          </div>

          <div className="personal-card-details">
            <div className="personal-card-field">
              <p className="personal-card-label">Especialidad</p>
              <p className="personal-card-value">{especialidad}</p>
            </div>

            <div className="personal-card-field">
              <p className="personal-card-label">Descripción</p>
              <p className="personal-card-value personal-card-description">{descripcion}</p>
            </div>

            <div className="personal-card-experience">
              <svg
                className="personal-card-experience-icon"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
              <span className="personal-card-experience-text">
                {aniosExperiencia} {aniosExperiencia === 1 ? 'año' : 'años'} de experiencia
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

