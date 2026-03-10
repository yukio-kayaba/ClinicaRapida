import type { FC } from 'react';
import { useMemo, useState } from 'react';
import { useReservasContext } from '../context/ReservasContext';
import { ReservaListSection } from '../components/ReservaListSection';
import { ConsultasViewBoostrap } from '@/controllers/ConsultasViewBoostrarp';

export const ReservasDashboard: FC = () => {
  const { reservas, confirmarReserva, rechazarReserva } = useReservasContext();

  const hoy = useMemo(() => new Date().toISOString().slice(0, 10), []);

  const [activeTab, setActiveTab] = useState<'espera' | 'respondidos'>('espera');

  const pendientes = reservas.filter(r => r.estado === 'ESPERA');
  const respondidos = reservas.filter(
    r => r.estado === 'CONFIRMADO' || r.estado === 'RECHAZADO',
  );

  return (
    <div className="dashboard-root">
      <ConsultasViewBoostrap />
      <header className="dashboard-header">
        <div>
          <h1>Sistema de reservas médicas</h1>
          <p>Dashboard de hoy · {hoy}</p>
        </div>
      </header>
      <main className="dashboard-layout">
        <section className="calendar-wrapper">
          <iframe
            src="https://calendar.google.com/calendar/embed?height=600&wkst=1&ctz=America%2FLima&showPrint=0&title=Reservaciones&showTz=0&showCalendars=0&src=OWE3NTZhODBjMTg4MjMzNWJiYzRmY2IzZTAzZjhiYzFmY2MzYzczZGUzMmY2YmE2YjQ0M2IzMjM3ZTA2ODFmZEBncm91cC5jYWxlbmRhci5nb29nbGUuY29t&color=%23795548"
            style={{
              borderWidth: "0",
              width: "100%",
              height: "100%",
              borderRadius: "10px",
            }}
            width="800"
            height="600"
            frameBorder="0"
            scrolling="no"
          ></iframe>
        </section>

        <aside className="side-panel">
          <div className="side-panel-tabs">
            <button
              type="button"
              className={`tab-button ${activeTab === 'espera' ? 'tab-button-active' : ''}`}
              onClick={() => setActiveTab('espera')}
            >
              En espera
            </button>
            <button
              type="button"
              className={`tab-button ${
                activeTab === 'respondidos' ? 'tab-button-active' : ''
              }`}
              onClick={() => setActiveTab('respondidos')}
            >
              Respondidos
            </button>
          </div>

          <div className="side-panel-content">
            {activeTab === 'espera' ? (
              <ReservaListSection
                title="Pendientes"
                color="yellow"
                reservas={pendientes}
                onConfirmar={confirmarReserva}
                onRechazar={rechazarReserva}
              />
            ) : (
              <ReservaListSection
                title="Respondidos"
                color="green"
                reservas={respondidos}
              />
            )}
          </div>
        </aside>
      </main>
    </div>
  );
};


