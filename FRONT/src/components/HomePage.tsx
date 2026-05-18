import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function HomePage() {
  const navigate = useNavigate()
  const auth = useAuth()

  const handleLogout = () => {
    // logout() del contexto limpia el token del localStorage Y resetea el estado de React
    auth.logout()
    navigate('/login')
  }

  return (
    <div className="card">
      <div className="app-header">
        <h1 className="titulo">¡Bienvenido a COMIDAPP!</h1>
      </div>
      <div className="bienvenida-badge">✅ Sesión iniciada correctamente</div>
      <div className="divider" />
      <button className="btn-agregar" onClick={() => navigate('/perfil')}>👤 Mi perfil</button>
      <button className="btn-secundario" onClick={handleLogout}>Cerrar sesión</button>
    </div>
  )
}

export default HomePage
