import { Navigate } from 'react-router-dom'
import type { ReactNode } from 'react'
import { useAuth } from '../context/AuthContext'

interface Props {
  children: ReactNode
  allowedRoles?: string[]   // si se omite allowedRoles, cualquier usuario logueado puede acceder.
}

function PrivateRoute({ children, allowedRoles }: Props) {
  const { token, rol } = useAuth()

  if (!token) { return <Navigate to="/login" replace /> } // user SIN loguear va para /login
  if (allowedRoles && rol && !allowedRoles.includes(rol)) { 
    return <Navigate to="/no-autorizado" replace /> // logueado pero con rol no  permitido va para /no-autorizado
  } 
  return <>{children}</> // si todo está bien renderiza los children (pág real)

}

export default PrivateRoute
