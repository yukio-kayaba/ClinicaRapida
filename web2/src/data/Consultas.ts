import { loginResponse, ResponseApi } from "@/types";
import { APPToken, dataCM, dataPruebaz } from "../const";

export class Consultas {
  constructor() {}

  static async POST(url: string, data: object) {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "z-project-token": `z ${APPToken}`,
      },
      body: JSON.stringify(data),
    });

    const resultado: ResponseApi = await response.json();
    
    if (resultado.status === "error") {
      throw new Error(resultado.message);
    }
    return resultado;
  }

  static async GET(url: string): Promise<ResponseApi> {
    const token: loginResponse | null = JSON.parse(
      localStorage.getItem(dataCM) || "{}",
    );

    if (!token || !token.tokenZ) {
      throw new Error("No tienes acceso para la consulta");
    }

    const response = await fetch(url, {
      method: "GET",
      headers: {
        Authorization: `bearer ${token.tokenZ}`,
        "Content-Type": "application/json",
        "z-project-token":`z ${APPToken}`,
      },
    });
    const verifi = "Token caducado , vuelva a iniciar sesion";
    const resultado: ResponseApi = await response.json();
    if (!response.ok) {
      if (response.status === 401 || response.status === 403) {
        if (resultado.error || resultado.error === verifi) {
          localStorage.removeItem(dataPruebaz);
        }
        throw new Error("unautorized");
      }
      throw new Error(
        `Error del servidor: ${response.status} ${response.statusText}`,
      );
    }

    if (resultado.status === "fail") {
      throw new Error(resultado.message);
    }
    return resultado;
  }

  static async POSTL(url: string, data: object) {
    const token: loginResponse | null = JSON.parse(
      localStorage.getItem(dataCM) || "{}",
    );
    if (!token || !token.tokenZ) {
      throw new Error("No tienes acceso para la consulta");
    }
    const response = await fetch(url, {
      method: "POST",
      headers: {
        Authorization: `bearer ${token.tokenZ}`,
        "Content-Type": "application/json",
        "z-project-token": `z ${APPToken}`,
      },
      body: JSON.stringify(data),
    });

    const verifi = "Token caducado , vuelva a iniciar sesion";
    const resultado: ResponseApi = await response.json();
    if (!response.ok) {
      if (response.status === 401 || response.status === 403) {
        if (resultado.error || resultado.error === verifi) {
          localStorage.removeItem(dataPruebaz);
        }
        throw new Error("unautorized");
      }
      throw new Error(
        `Error del servidor: ${response.status} ${response.statusText}`,
      );
    }

    if (resultado.status === "fail") {
      throw new Error(resultado.message);
    }
    return resultado;
  }
}
