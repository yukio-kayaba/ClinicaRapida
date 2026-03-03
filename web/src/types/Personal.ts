export type RolPersonal = "doctor" | "enfermero" | "obstetra";

export interface Personal {
  id: string;
  nombre: string;
  rol: RolPersonal;
  especialidad: string;
  descripcion: string;
  aniosExperiencia: number;
  foto?: string; // URL de la foto del personal
}

