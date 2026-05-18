type Rol = 'ADMIN' | 'CLIENTE' | 'REPARTIDOR' | null

interface TipoAuthContext {
  token: string | null
  rol: Rol
  login: (token: string) => void
  logout: () => void
}

export type { TipoAuthContext, Rol }