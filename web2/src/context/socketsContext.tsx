import { createContext, useContext, useEffect } from "react";
import { getSocket } from "../socketConexion";

const SocketContext = createContext(getSocket);

export const SocketProvider = ({ children }: { children: React.ReactNode }) => {
  useEffect(() => {
    getSocket.connect();
    return () => {
      getSocket.disconnect();
    };
  }, []);

  return (
    <SocketContext.Provider value={getSocket}>
      {children}
    </SocketContext.Provider>
  );
};

export const useSocket = () => useContext(SocketContext);
