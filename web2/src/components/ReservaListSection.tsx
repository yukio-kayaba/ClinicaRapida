import type { FC } from 'react';
import { useState } from 'react';
import type { Reserva } from '../types/reserva';

interface ReservaListSectionProps {
  title: string;
  color: 'yellow' | 'green' | 'red';
  reservas: Reserva[];
  onConfirmar?: (id: Reserva['idreservas']) => void;
  onRechazar?: (id: Reserva['idreservas']) => void;
}

const colorMap: Record<ReservaListSectionProps['color'], string> = {
  yellow: 'badge-pending',
  green: 'badge-confirmed',
  red: 'badge-rejected',
};

export const ReservaListSection: FC<ReservaListSectionProps> = ({
  title,
  color,
  reservas,
  onConfirmar,
  onRechazar,
}) => {
  const [selectedReserva, setSelectedReserva] = useState<Reserva | null>(null);

  const handleCloseModal = () => setSelectedReserva(null);

  const handleConfirmar = () => {
    if (selectedReserva && onConfirmar) {
      onConfirmar(selectedReserva.idreservas);
      handleCloseModal();
    }
  };

  const handleRechazar = () => {
    if (selectedReserva && onRechazar) {
      onRechazar(selectedReserva.idreservas);
      handleCloseModal();
    }
  };

  return (
    <section className="panel-section">
      <header className="panel-section-header">
        <h2>{title}</h2>
        <span className={`badge ${colorMap[color]}`}>{reservas.length}</span>
      </header>
      <div className="panel-section-body">
        {reservas.length === 0 && <p className="empty-text">No hay reservas</p>}
        {reservas.map((reserva) => (
          <article
            key={reserva.idreservas}
            className="reserva-card"
            onClick={() => setSelectedReserva(reserva)}
          >
            <div className="reserva-card-main">
              <div className="reserva-card-time">
                <span>{reserva.hora}</span>
                <span className="time-range-separator">·</span>
                <span>{reserva.fecha}</span>
              </div>
              <div className="reserva-card-info">
                <p className="reserva-paciente">{reserva.nombre}</p>
                <p className="reserva-contacto">{reserva.motivo}</p>
              </div>
              <span
                className={`reserva-status-chip status-${reserva.estado.toLowerCase()}`}
              >
                {reserva.estado.toLowerCase()}
              </span>
            </div>
          </article>
        ))}
      </div>

      {selectedReserva && (
        <div className="reserva-modal-backdrop" onClick={handleCloseModal}>
          <div className="reserva-modal" onClick={(e) => e.stopPropagation()}>
            <header className="reserva-modal-header">
              <h3>
                {selectedReserva.nombre} {selectedReserva.apellido}
              </h3>
              <span
                className={`reserva-status-chip status-${selectedReserva.estado.toLowerCase()}`}
              >
                {selectedReserva.estado.toLowerCase()}
              </span>
            </header>

            <div className="reserva-modal-body">
              <div className="reserva-modal-row">
                <span className="label">Fecha</span>
                <span>{selectedReserva.fecha}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">Hora</span>
                <span>{selectedReserva.hora}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">DNI</span>
                <span>{selectedReserva.dni}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">Teléfono</span>
                <span>{selectedReserva.telefono}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">Correo</span>
                <span>{selectedReserva.correo}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">Motivo</span>
                <span>{selectedReserva.motivo}</span>
              </div>
              <div className="reserva-modal-row">
                <span className="label">Creado</span>
                <span>
                  {new Date(selectedReserva.fechacreacion).toLocaleDateString(
                    "es-PE",
                    { weekday: "long", day: "numeric", month: "long" },
                  )}
                </span>
              </div>
            </div>

            {selectedReserva.estado === "ESPERA" && (
              <div className="reserva-modal-actions">
                {onConfirmar && (
                  <button
                    className="btn btn-primary"
                    type="button"
                    onClick={handleConfirmar}
                  >
                    Confirmar reserva
                  </button>
                )}
                {onRechazar && (
                  <button
                    className="btn btn-ghost"
                    type="button"
                    onClick={handleRechazar}
                  >
                    Rechazar reserva
                  </button>
                )}
              </div>
            )}
          </div>
        </div>
      )}
    </section>
  );
};


