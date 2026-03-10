import { io } from "socket.io-client";
import { RUTA,dataPruebaz } from "./const";

export const getSocket = io(RUTA, {
  autoConnect: false,
  auth: {
    token: localStorage.getItem(dataPruebaz),
  },
});
