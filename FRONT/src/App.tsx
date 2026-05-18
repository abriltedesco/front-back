import './App.css'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import FormReg from './components/FormReg'
import FormLogIn from './components/FormLogIn'
import HomePage from './components/HomePage'
import PerfilPage from './components/PerfilPage'
import { AuthProvider } from './context/AuthContext'
import PrivateRoute from './components/PrivateRoute'

function App() {
  return (
    // BrowserRouter habilita el enrutado basado en la URL del navegador.
    // AuthProvider va adentro para que sus hijos puedan usar el enrutador si lo necesitan.
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<FormLogIn />} />
          <Route path="/register" element={<FormReg />} />
          

          <Route path="/home"  element={ <PrivateRoute allowedRoles={['CLIENTE']}> <HomePage /></PrivateRoute>} />
          <Route path="/perfil"  element={ <PrivateRoute> <PerfilPage /></PrivateRoute>} />
        
          {/*
            <Route
              path="/admin"
              element={
                <PrivateRoute allowedRoles={['ADMIN']}>
                  <AdminPage />
                </PrivateRoute>
              }
            />
            <Route
              path="/repartidor"
              element={
                <PrivateRoute allowedRoles={['REPARTIDOR']}>
                  <RepartidorPage />
                </PrivateRoute>
              }
            />
          */}

          <Route path="/no-autorizado" element={<p style={{ padding: '2rem' }}>No tenés permisos para acceder a esta página.</p>} />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}

export default App
