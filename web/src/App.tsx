import { useState } from 'react';
import { PersonalProvider } from './context/PersonalContext';
import { Navbar } from './components/Navbar/Navbar';
import { ViewPersonal } from './components/personalView/ViewPersonal';
import { RolPersonal } from './types/Personal';

function App() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRol, setSelectedRol] = useState<RolPersonal | 'todos'>('todos');

  return (
    <PersonalProvider>
      <Navbar 
        searchTerm={searchTerm}
        onSearchChange={setSearchTerm}
        selectedRol={selectedRol}
        onRolFilterChange={setSelectedRol}
      />
      <ViewPersonal 
        searchTerm={searchTerm}
        selectedRol={selectedRol}
      />
    </PersonalProvider>
  );
}

export default App;

