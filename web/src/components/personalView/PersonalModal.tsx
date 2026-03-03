import { Personal } from '../../types/Personal';
import './PersonalModal.css';

interface PersonalModalProps {
  personal: Personal | null;
  isOpen: boolean;
  onClose: () => void;
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

const getRolColor = (rol: Personal['rol']): string => {
  switch (rol) {
    case 'doctor':
      return 'personal-modal-rol-doctor';
    case 'enfermero':
      return 'personal-modal-rol-enfermero';
    case 'obstetra':
      return 'personal-modal-rol-obstetra';
    default:
      return '';
  }
};

// Foto temporal - más adelante vendrá del backend
// Por ahora usamos una foto genérica de doctor para todos
const DEFAULT_PHOTO = 'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400&h=400&fit=crop';

export const PersonalModal = ({ personal, isOpen, onClose }: PersonalModalProps) => {
  if (!isOpen || !personal) return null;

  const { nombre, rol, especialidad, descripcion, aniosExperiencia } = personal;
  // Por ahora usar la foto por defecto, más adelante vendrá del backend
  const foto = DEFAULT_PHOTO;

  const handleBackdropClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className="personal-modal-backdrop" onClick={handleBackdropClick}>
      <div className="personal-modal">
        <button className="personal-modal-close" onClick={onClose} aria-label="Cerrar">
          <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>

        <div className="personal-modal-content">
          <div className="personal-modal-header">
            <div className="personal-modal-photo-container">
              <img 
                src={foto} 
                alt={nombre}
                className="personal-modal-photo"
                onError={(e) => {
                  const target = e.target as HTMLImageElement;
                  target.style.display = 'none';
                }}
              />
            </div>
            <div className="personal-modal-header-info">
              <h2 className="personal-modal-name">{nombre}</h2>
              <span className={`personal-modal-rol ${getRolColor(rol)}`}>
                {getRolLabel(rol)}
              </span>
            </div>
          </div>

          <div className="personal-modal-body">
            <div className="personal-modal-section">
              <h3 className="personal-modal-section-title">Especialidad</h3>
              <p className="personal-modal-section-content">{especialidad}</p>
            </div>

            <div className="personal-modal-section">
              <h3 className="personal-modal-section-title">Experiencia</h3>
              <p className="personal-modal-section-content">
                {aniosExperiencia} {aniosExperiencia === 1 ? 'año' : 'años'} de experiencia profesional
              </p>
            </div>

            <div className="personal-modal-section">
              <h3 className="personal-modal-section-title">Descripción</h3>
              <p className="personal-modal-section-content personal-modal-description">
                {descripcion}
              </p>
            </div>

            <div className="personal-modal-section">
              <h3 className="personal-modal-section-title">Información Adicional</h3>
              <div className="personal-modal-info-grid">
                <div className="personal-modal-info-item">
                  <svg className="personal-modal-info-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                  <span>Profesional Certificado</span>
                </div>
                <div className="personal-modal-info-item">
                  <svg className="personal-modal-info-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                  <span>Disponible 24/7</span>
                </div>
                <div className="personal-modal-info-item">
                  <svg className="personal-modal-info-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                    />
                  </svg>
                  <span>Historial Completo</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

