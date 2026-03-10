export type EstadoReserva = 'ESPERA' | 'CONFIRMADO' | 'RECHAZADO';

export interface Reserva {
  apellido: string;
  correo: string;
  dni: string;
  estado: EstadoReserva;
  fecha: string;
  fechacreacion: string;
  hora: string;
  idpersonalacceso: number;
  idproyecto: number;
  idreservas: number;
  motivo: string;
  nombre: string;
  telefono:string;
}


