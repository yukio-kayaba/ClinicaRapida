import { io } from "socket.io-client";
import { RUTA,dataCM } from "./const";

export const getSocket = io(RUTA, {
  autoConnect: false,
  auth: {
    token: localStorage.getItem(dataCM),
  },
});
