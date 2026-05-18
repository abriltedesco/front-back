import type { Usuario } from '../interfaces/Usuario'
const urlBase = 'http://localhost:8080/auth'
const urlUsuarios = 'http://localhost:8080/usuarios'

export async function registrarUsuario(usuario: Usuario): Promise<void> {
  const response = await fetch(`${urlBase}/registro/cliente`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(usuario),
  })

  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || 'Error al registrar el usuario')
  }
}

export async function loginUsuario(mail: string, contrasenia: string): Promise<string> {
  const response = await fetch(`${urlBase}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ mail, contrasenia }),
  })

  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || 'Credenciales incorrectas')
  }

  const token = await response.text()
  localStorage.setItem('token', token)
  return token
}

// usa esta función en fetch que requiera estar autenticado
export function getAuthHeader(): Record<string, string> {
  const token = localStorage.getItem('token')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export function logout(): void {
  localStorage.removeItem('token')
}

export function estaLogueado(): boolean {
  return !!localStorage.getItem('token')
}

// decodifica el payload del JWT y extrae el rol del user
export function identificaRol(token: string): string | null {
  try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')
    const payload = JSON.parse(atob(base64))


    // chequea como llega el rol, puede ser de 3 fromas:
    if (typeof payload.tipo === 'string') return payload.tipo     // { "tipo": "ADMIN" }  ← su caso
    if (typeof payload.role === 'string') return payload.role     // { "role": "ADMIN" }
    if (Array.isArray(payload.roles)) return payload.roles[0] ?? null     //  { "roles": ["ADMIN"] }
    if (Array.isArray(payload.authorities)) {     // { "authorities": [{"authority":"ROLE_ADMIN"}] }
      const first = payload.authorities[0]
      if (typeof first === 'string') return first.replace('ROLE_', '')
      if (typeof first?.authority === 'string') return first.authority.replace('ROLE_', '')
    }

    return null
  } 
  catch {
    return null
  }
}

export function getIdFromToken(token: string): string | null {
  try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')
    const payload = JSON.parse(atob(base64))
    return payload.dni ?? payload.sub ?? null
  } catch {
    return null
  }
}

export async function obtenerPerfil(dni: string, token: string): Promise<Usuario> {
  const response = await fetch(`${urlUsuarios}/mostrar/${dni}`, {
    headers: { 'Authorization': `Bearer ${token}` },
  })
  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || 'Error al obtener el perfil')
  }
  return response.json() as Promise<Usuario>
}

export async function actualizarPerfil(dni: string, datos: { contrasenia?: string; telefono?: number }, perfilActual: Record<string, unknown>, token: string): Promise<void> {
  // Armamos el objeto completo que espera el back (POST /usuarios/modificar)
  // Excluimos la contrasenia del perfil actual para no mandar el hash viejo
  const { contrasenia: _ignorar, ...perfilSinContrasenia } = perfilActual
  void _ignorar
  const payload = {
    ...perfilSinContrasenia,
    dni: Number(dni),
    ...(datos.telefono !== undefined ? { telefono: datos.telefono } : {}),
    ...(datos.contrasenia ? { contrasenia: datos.contrasenia } : {}),
  }
  const response = await fetch(`${urlUsuarios}/modificar`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
    body: JSON.stringify(payload),
  })
  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || 'Error al actualizar el perfil')
  }
}
