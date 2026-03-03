import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { Personal } from '../types/Personal';

interface PersonalContextType {
  personal: Personal[];
  loading: boolean;
  addPersonal: (personal: Personal) => void;
  setPersonal: (personal: Personal[]) => void;
  fetchPersonalFromAPI: () => Promise<void>;
}

const PersonalContext = createContext<PersonalContextType | undefined>(undefined);

const STORAGE_KEY = 'personal_medico';

// Datos de prueba que simulan respuesta de API
const MOCK_API_DATA: Personal[] = [
  {
    id: '1',
    nombre: 'Dr. María González',
    rol: 'doctor',
    especialidad: 'Cardiología',
    descripcion: 'Especialista en enfermedades cardiovasculares con amplia experiencia en cirugías de corazón.',
    aniosExperiencia: 15,
    foto: 'https://i.pravatar.cc/150?img=47',
  },
  {
    id: '2',
    nombre: 'Enfermera Ana Martínez',
    rol: 'enfermero',
    especialidad: 'Cuidados Intensivos',
    descripcion: 'Experta en cuidados críticos y atención de pacientes en estado grave.',
    aniosExperiencia: 8,
    foto: 'https://i.pravatar.cc/150?img=45',
  },
  {
    id: '3',
    nombre: 'Dr. Carlos Rodríguez',
    rol: 'doctor',
    especialidad: 'Pediatría',
    descripcion: 'Pediatra especializado en atención neonatal y enfermedades infantiles.',
    aniosExperiencia: 12,
    foto: 'https://i.pravatar.cc/150?img=12',
  },
  {
    id: '4',
    nombre: 'Obstetra Laura Sánchez',
    rol: 'obstetra',
    especialidad: 'Ginecología y Obstetricia',
    descripcion: 'Especialista en atención prenatal, partos y cuidados postnatales.',
    aniosExperiencia: 10,
    foto: 'https://i.pravatar.cc/150?img=20',
  },
  {
    id: '5',
    nombre: 'Enfermero Juan Pérez',
    rol: 'enfermero',
    especialidad: 'Emergencias',
    descripcion: 'Experto en atención de emergencias médicas y primeros auxilios.',
    aniosExperiencia: 6,
    foto: 'https://i.pravatar.cc/150?img=33',
  },
  {
    id: '6',
    nombre: 'Dr. Sofía Ramírez',
    rol: 'doctor',
    especialidad: 'Neurología',
    descripcion: 'Neuróloga especializada en trastornos del sistema nervioso y enfermedades neurológicas.',
    aniosExperiencia: 18,
    foto: 'https://i.pravatar.cc/150?img=27',
  },
  {
    id: '7',
    nombre: 'Obstetra Carmen López',
    rol: 'obstetra',
    especialidad: 'Obstetricia de Alto Riesgo',
    descripcion: 'Especialista en embarazos de alto riesgo y complicaciones obstétricas.',
    aniosExperiencia: 14,
    foto: 'https://i.pravatar.cc/150?img=32',
  },
  {
    id: '8',
    nombre: 'Enfermera Patricia Torres',
    rol: 'enfermero',
    especialidad: 'Oncología',
    descripcion: 'Especialista en cuidados oncológicos y apoyo a pacientes con cáncer.',
    aniosExperiencia: 9,
    foto: 'https://i.pravatar.cc/150?img=44',
  },
];

// Simular llamada a API
const fetchPersonalAPI = async (): Promise<Personal[]> => {
  // Simular delay de red
  await new Promise((resolve) => setTimeout(resolve, 1000));
  return MOCK_API_DATA;
};

interface PersonalProviderProps {
  children: ReactNode;
}

export const PersonalProvider = ({ children }: PersonalProviderProps) => {
  const [personal, setPersonalState] = useState<Personal[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // Función para obtener datos de la API
  const fetchPersonalFromAPI = async () => {
    setLoading(true);
    try {
      const data = await fetchPersonalAPI();
      setPersonalState(data);
    } catch (error) {
      console.error('Error al obtener datos de la API:', error);
    } finally {
      setLoading(false);
    }
  };

  // Inicializar desde localStorage o API
  useEffect(() => {
    const initializeData = async () => {
      try {
        const stored = localStorage.getItem(STORAGE_KEY);
        if (stored) {
          const parsed = JSON.parse(stored);
          // Si hay datos en localStorage, usarlos
          if (parsed.length > 0) {
            setPersonalState(parsed);
            setLoading(false);
            return;
          }
        }
        // Si no hay datos en localStorage, cargar desde API
        await fetchPersonalFromAPI();
      } catch (error) {
        console.error('Error al inicializar datos:', error);
        setLoading(false);
      }
    };

    initializeData();
  }, []);

  // Sincronizar con localStorage cuando cambie el estado
  useEffect(() => {
    if (!loading) {
      try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(personal));
      } catch (error) {
        console.error('Error al guardar datos en localStorage:', error);
      }
    }
  }, [personal, loading]);

  const addPersonal = (nuevoPersonal: Personal) => {
    setPersonalState((prev) => [...prev, nuevoPersonal]);
  };

  const setPersonal = (nuevoPersonal: Personal[]) => {
    setPersonalState(nuevoPersonal);
  };

  // Preparado para futura conexión con sockets
  // Aquí se puede agregar lógica de WebSocket cuando sea necesario
  // useEffect(() => {
  //   const socket = new WebSocket('ws://...');
  //   socket.onmessage = (event) => {
  //     const data = JSON.parse(event.data);
  //     setPersonalState(data);
  //   };
  //   return () => socket.close();
  // }, []);

  return (
    <PersonalContext.Provider
      value={{
        personal,
        loading,
        addPersonal,
        setPersonal,
        fetchPersonalFromAPI,
      }}
    >
      {children}
    </PersonalContext.Provider>
  );
};

export const usePersonal = (): PersonalContextType => {
  const context = useContext(PersonalContext);
  if (context === undefined) {
    throw new Error('usePersonal debe ser usado dentro de PersonalProvider');
  }
  return context;
};

