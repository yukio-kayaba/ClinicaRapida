import type { FC, FormEvent } from 'react';
import { useState } from 'react';
import { useAuthContext } from '../context/AuthContext';

export const LoginPage: FC = () => {
  const { login, isLoading, error } = useAuthContext();
  const [email, setEmail] = useState('admin@clinicarapida.com');
  const [password, setPassword] = useState('');
  const [mensaje,setmensaje] = useState("");
  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await login({ email, password }).catch(err=>{
      console.log(err);
      setmensaje(err)
    });
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <header className="auth-header">
          <h1>Clínica Monje Medrano</h1>
          <p>Inicia sesión para acceder al panel de reservas.</p>
        </header>

        <form className="auth-form" onSubmit={handleSubmit}>
          <label className="auth-field">
            <span>Correo</span>
            <input
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              placeholder="tu@gmail.com"
              disabled={isLoading}
              required
            />
          </label>

          <label className="auth-field">
            <span>Contraseña</span>
            <input
              type="password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              placeholder="••••••••"
              disabled={isLoading}
              required
            />
          </label>

          {error && <p className="auth-error">{error}</p>}

          <button className="auth-submit" type="submit" disabled={isLoading}>
            {isLoading ? 'Ingresando...' : 'Ingresar'}
          </button>
        </form>
        <p> {mensaje} </p>
      </div>
    </div>
  );
};


