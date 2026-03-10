import React, { createContext, useContext, useEffect, useState } from 'react';
import type { Reserva, EstadoReserva } from '../types/reserva';
import { RESERVAS_MOCK } from '../data/reservasMock';
import { useSocket } from './socketsContext';
import { Consultas } from '@/data/Consultas';
import { RUTA } from '@/const';
import Swal from 'sweetalert2';

export interface ReservasContextType {
  reservas: Reserva[];
  addReserva: (reserva: Omit<Reserva, 'idreservas' | 'fechacreacion' | 'estado'>) => Promise<Reserva>;
  editReserva: (id: Reserva['idreservas'], datos: Partial<Reserva>) => void;
  deleteReserva: (id: Reserva['idreservas']) => void;
  loadReservas: (data: Reserva[] | undefined) => void;
  cambiarEstado: (id: Reserva['idreservas'], estado: EstadoReserva) => void;
  confirmarReserva: (id: Reserva['idreservas']) => void;
  rechazarReserva: (id: Reserva['idreservas']) => void;
}

const ReservasContext = createContext<ReservasContextType | undefined>(undefined);

const RESERVAS_STORAGE_KEY = 'reservas_medicas';

export const ReservasProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const socket = useSocket();
  const [reservas, setReservas] = useState<Reserva[]>(() => {
  
    const stored = localStorage.getItem(RESERVAS_STORAGE_KEY);
    if (stored && stored !== '{}') {
      try {
        return JSON.parse(stored) as Reserva[];
      } catch {
        return RESERVAS_MOCK;
      }
    }
    return RESERVAS_MOCK;
  });

  useEffect(() => {
    localStorage.setItem(RESERVAS_STORAGE_KEY, JSON.stringify(reservas));
  }, [reservas]);

  useEffect(()=>{
    if(!socket)return;

    const getReserva = (reserva:Reserva)=>{
      setReservas((prev)=>{
        if(prev.some(re => re.idreservas === reserva.idreservas))return prev;
        return [...prev,reserva]
      })
    }

    const updateReserva = (reserva:Reserva)=>{
      setReservas(rese =>
        rese.map(r=> r.idreservas === reserva.idreservas? {...r,...reserva}:r,)
      );
    }

    socket.on("server::CM::reserva", getReserva);
    socket.on("server::CM::reservaConfirm",updateReserva);
    return ()=>{
      socket.off("server::CM::reserva", getReserva);
      socket.off("server::CM::reservaConfirm",updateReserva);
    }
  },[socket])

  const addReserva = async (
    reserva: Omit<Reserva, 'idreservas' | 'fechacreacion' | 'estado'>,
  ): Promise<Reserva> => {
    const nueva: Reserva = {
      ...reserva,
      idreservas: Date.now(),
      estado: 'ESPERA',
      fechacreacion: new Date().toISOString(),
    };
    setReservas(prev => [...prev, nueva]);
    return nueva;
  };

  const editReserva = (id: Reserva['idreservas'], datos: Partial<Reserva>) => {
    setReservas(prev => prev.map(r => (r.idreservas === id ? { ...r, ...datos } : r)));
  };

  const deleteReserva = (id: Reserva['idreservas']) => {
    setReservas(prev => prev.filter(r => r.idreservas !== id));
  };

  const loadReservas = (data: Reserva[] | undefined) => {
    if (data) {
      setReservas(data);
      localStorage.setItem(RESERVAS_STORAGE_KEY, JSON.stringify(data));
    }
  };

  const cambiarEstado =  async(id: Reserva['idreservas'], estado: EstadoReserva) => {
    const data = {
      idreservas: id,
      estado: estado,
    };
    await Consultas.POSTL(`${RUTA}/api/centromedico/reservaciones/up`,data).then(datos=>{
      console.log(datos);
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Confirmado con exito",
        showConfirmButton: false,
        timer: 1500,
      });
    }).catch(error=>{
      console.log(error);
      Swal.fire({
        position: "center",
        icon: "error",
        title: "No se pudo actualizar la reservacion",
        showConfirmButton: false,
        timer: 1500,
      });
    });
  };

  const confirmarReserva = (id: Reserva['idreservas']) => {
    cambiarEstado(id, 'CONFIRMADO');
  };

  const rechazarReserva = (id: Reserva['idreservas']) => {
    cambiarEstado(id, 'RECHAZADO');
  };

  return (
    <ReservasContext.Provider
      value={{
        reservas,
        addReserva,
        editReserva,
        deleteReserva,
        loadReservas,
        cambiarEstado,
        confirmarReserva,
        rechazarReserva,
      }}
    >
      {children}
    </ReservasContext.Provider>
  );
};

export const useReservasContext = (): ReservasContextType => {
  const context = useContext(ReservasContext);
  if (!context) {
    throw new Error('useReservasContext debe usarse dentro de ReservasProvider');
  }
  return context;
};


