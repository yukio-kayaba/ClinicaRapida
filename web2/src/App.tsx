import './App.css';
import { ReservasProvider } from './context/ReservasContext';
import { ReservasDashboard } from './pages/ReservasDashboard';
import { AuthProvider, useAuthContext } from './context/AuthContext';
import { LoginPage } from './pages/LoginPage';
import { SocketProvider } from './context/socketsContext';

function AppContent() {
  const { user } = useAuthContext();

  if (user == null) {
    return <LoginPage />;
  }

  return (
    <SocketProvider>
      <ReservasProvider>
        <ReservasDashboard />
      </ReservasProvider>
    </SocketProvider>
  );
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
