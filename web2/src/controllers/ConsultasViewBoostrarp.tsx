import { RUTA } from "@/const";
import { Consultas } from "@/data/Consultas";
import { userReservas } from "@/hooks/userReservas"
import { useEffect } from "react";

export const ConsultasViewBoostrap = ()=>{
    const { loadReservas } = userReservas();

    useEffect(()=>{
        const cargarReservas = async ()=>{
            try {
                const consultaResponse = await Consultas.GET(
                  `${RUTA}/api/centromedico/reservaciones`,
                )
                console.log(consultaResponse.data);
                loadReservas(consultaResponse.data);
            } catch (error) {
                console.log(error);
            }
        }
        cargarReservas();
    },[]);
    return null;
}
