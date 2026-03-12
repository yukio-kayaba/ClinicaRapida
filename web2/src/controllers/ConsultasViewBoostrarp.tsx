import { RUTA } from "@/const";
import { Consultas } from "@/data/Consultas";
import { userReservas } from "@/hooks/userReservas"
import { Reserva } from "@/types/reserva";
import { useEffect } from "react";

export const ConsultasViewBoostrap = ()=>{
    const { loadReservas } = userReservas();

    useEffect(()=>{
        const cargarReservas = async ()=>{
            try {
                const consultaResponse = await Consultas.GET(
                  `${RUTA}/api/centromedico/reservaciones`,
                )
                if(consultaResponse.data !== undefined){
                    const reserva1 = consultaResponse.data as Reserva[];    
                    loadReservas(reserva1);
                }
            } catch (error) {
                console.log(error);
            }
        }
        cargarReservas();
    },[]);
    return null;
}
