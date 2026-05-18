import { createContext, useContext, useState } from 'react'
import type { ReactNode } from 'react'
import { identificaRol } from '../service/autenticacion'
import type { TipoAuthContext, Rol } from '../interfaces/TipoAuthContext'


// createContext crea un "canal global" al que cualquier componente hijo puede
// suscribirse sin necesidad de pasar props manualmente por cada nivel.
const AuthContext = createContext<TipoAuthContext | null>(null)

function tokenEsValido(token: string): boolean {
  try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')
    const { exp } = JSON.parse(atob(base64))
    return exp ? exp * 1000 > Date.now() : true  // exp está en segundos y Date.now() en milisegundos
  } catch {
    return false
  }
}

// "envuelve" la app y cualquier componente dentro de él puede llamar a useAuth() para leer o modificar el estado de sesión.
export function AuthProvider({ children }: { children: ReactNode }) {
  const storedToken = localStorage.getItem('token')
  const tokenInicial = storedToken && tokenEsValido(storedToken) ? storedToken : null
  if (storedToken && !tokenInicial) localStorage.removeItem('token')   // token expirado --> eliminado
  const [token, setToken] = useState<string | null>(tokenInicial)
  const [rol, setRol] = useState<Rol>(tokenInicial ? (identificaRol(tokenInicial) as Rol) : null)

  function login(nuevoToken: string) {
    localStorage.setItem('token', nuevoToken)
    setToken(nuevoToken)
    setRol(identificaRol(nuevoToken) as Rol)
  }

  function logout() {
    localStorage.removeItem('token')
    setToken(null)
    setRol(null)
  }

  return (
    <AuthContext.Provider value={{ token, rol, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

// para acceder al contexto. si se llama fuera de AuthProvider, lanza un error 
export function useAuth(): TipoAuthContext {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth debe usarse dentro de <AuthProvider>')
  return ctx
}
