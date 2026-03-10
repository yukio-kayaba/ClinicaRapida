import type { Reserva } from '../types/reserva';

const hoy = new Date();

const formatDate = (d: Date): string => d.toISOString().slice(0, 10);

const addDays = (d: Date, days: number): string => {
  const copy = new Date(d);
  copy.setDate(copy.getDate() + days);
  return formatDate(copy);
};

export const RESERVAS_MOCK: Reserva[] = [
  {
    idreservas: 1,
    idproyecto: 1,
    idpersonalacceso: 1,
    nombre: 'Juan',
    apellido: 'Pérez',
    dni: '12345678',
    correo: 'juan.perez@example.com',
    telefono: '555-1234',
    fecha: addDays(hoy, 0),
    hora: '09:00',
    motivo: 'Consulta general',
    estado: 'PENDIENTE',
    fechacreacion: new Date().toISOString(),
  },
  {
    idreservas: 2,
    idproyecto: 1,
    idpersonalacceso: 2,
    nombre: 'María',
    apellido: 'Gómez',
    dni: '87654321',
    correo: 'maria.gomez@example.com',
    telefono: '555-5678',
    fecha: addDays(hoy, 1),
    hora: '10:00',
    motivo: 'Pediatría',
    estado: 'CONFIRMADO',
    fechacreacion: new Date().toISOString(),
  },
  {
    idreservas: 3,
    idproyecto: 1,
    idpersonalacceso: 3,
    nombre: 'Carlos',
    apellido: 'López',
    dni: '11223344',
    correo: 'carlos.lopez@example.com',
    telefono: '555-9012',
    fecha: addDays(hoy, -1),
    hora: '11:00',
    motivo: 'Traumatología',
    estado: 'RECHAZADO',
    fechacreacion: new Date().toISOString(),
  },
];

