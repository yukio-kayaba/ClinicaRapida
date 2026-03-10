export interface Permiso {
  idpermiso: number;
  nombre: string;
  descripcion: string;
  estado: number;
}

export interface Producto {
  activo: boolean;
  idproducto: number;
  nombrecomercial: string;
  descripcion: string;
  idcategoria: number;
  preciocompra: number;
  precioventa: number;
  stockactual: number;
  stockminimo: number;
  fecharegistro?: Date;
}

export interface Categorias {
  idcategoria: number;
  nombrecategoria: string;
  descripcion: string;
  estado: boolean;
}

export interface UsuarioContext {
  user: loginResponse | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

export interface ResponseApi {
  status: "fail" | "success" | "error";
  message: string;
  data: unknown;
  error?: string;
}

export interface loginResponse {
  nombreUser: string;
  tokenZ: string;
}

export interface FormData {
  nombre: string;
  descripcion: string;
  preciocompra: string;
  precioventa: string;
  idcategoria: number;
  disponible: boolean;
  stockactual: string;
  stockminimo: string;
}

export interface usuarioFormData {
  nombre: string;
  activo: boolean;
  diasTrabjado: string;
  dni: string;
  correo: string;
  contra: string;
}
