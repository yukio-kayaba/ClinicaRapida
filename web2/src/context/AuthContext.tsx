import React, { createContext, useContext, useEffect, useState } from 'react';
import type { AuthUser, LoginCredentials } from '../types/auth';
import { dataCM, RESERVAS_STORAGE_KEY, RUTA } from '@/const';
import { Consultas } from '@/data/Consultas';

interface AuthContextType {
  user: AuthUser | null;
  login: (credentials: LoginCredentials) => Promise<void>;
  logout: () => void;
  isLoading: boolean;
  error: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: React.ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<AuthUser | null>(() => {
    const stored = localStorage.getItem(dataCM);
    return stored ? JSON.parse(stored) : null;
  });;
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (user) {
      localStorage.setItem(dataCM, JSON.stringify(user));
    } else {
      localStorage.removeItem(dataCM);
    }
  }, [user]);

  const login = async ({ email, password }: LoginCredentials) => {
    setIsLoading(true);
    setError(null);
      try {
        const datos = {
          usuario:email,
          password
        }
        const data = await Consultas.POST(`${RUTA}/auth/loginCM`,datos);

        const response = data.data as AuthUser;
        localStorage.setItem(dataCM,JSON.stringify(response));
        setUser(response);
      } catch (err) {
        const error = err as Error;
        console.log(error);
        setError((err as Error).message);
        setUser(null);
      } finally {
        setIsLoading(false);
      }
    };

  const logout = () => {
    setUser(null);
    localStorage.removeItem(RESERVAS_STORAGE_KEY);
  };

  const value: AuthContextType = {
    user,
    login,
    logout,
    isLoading,
    error,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuthContext = (): AuthContextType => {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuthContext debe usarse dentro de AuthProvider');
  }
  return ctx;
};


