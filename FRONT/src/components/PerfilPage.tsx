import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext'
import { obtenerPerfil, actualizarPerfil, getIdFromToken } from '../service/autenticacion'
import type { Usuario } from '../interfaces/Usuario'

type FormEditarPerfil = {
  telefono: string          // los inputs HTML siempre devuelven string
  contrasenia: string
  confirmarContrasenia: string
}

function PerfilPage() {
  const { token } = useAuth()
  const navigate = useNavigate()

  const [perfil, setPerfil] = useState<Usuario | null>(null)
  const [cargando, setCargando] = useState(true)
  const [errorCarga, setErrorCarga] = useState<string | null>(null)
  const [editando, setEditando] = useState(false)
  const [errorEdicion, setErrorEdicion] = useState<string | null>(null)
  const [successMsg, setSuccessMsg] = useState<string | null>(null)

  const { register, handleSubmit, formState: { errors }, reset, watch } = useForm<FormEditarPerfil>()
  const contraseniaActual = watch('contrasenia')

  useEffect(() => {
    if (!token) return

    const id = getIdFromToken(token)
    if (!id) {
      setErrorCarga('No se pudo obtener el ID del usuario del token.')
      setCargando(false)
      return
    }

    obtenerPerfil(id, token)
      .then(data => {
        setPerfil(data)
        reset({ telefono: String(data.telefono), contrasenia: '', confirmarContrasenia: '' })
      })
      .catch(err => setErrorCarga(err instanceof Error ? err.message : 'Error al cargar el perfil'))
      .finally(() => setCargando(false))
  }, [token, reset])

  const onSubmit = async (data: FormEditarPerfil) => {
    if (!token || !perfil) return
    const id = getIdFromToken(token)
    if (!id) return

    try {
      setErrorEdicion(null)

      // Solo mandamos lo que cambió
      const payload: { telefono?: number; contrasenia?: string } = {}
      const telefonoNuevo = Number(data.telefono)
      if (data.telefono && telefonoNuevo !== perfil.telefono)
        payload.telefono = telefonoNuevo
      if (data.contrasenia)
        payload.contrasenia = data.contrasenia

      if (Object.keys(payload).length === 0) {
        setErrorEdicion('No modificaste ningún dato.')
        return
      }

      await actualizarPerfil(id, payload, perfil as unknown as Record<string, unknown>, token)

      // Actualizamos el estado local con los nuevos valores
      setPerfil({ ...perfil, ...payload })
      setSuccessMsg('¡Perfil actualizado correctamente!')
      setEditando(false)
    } catch (err) {
      setErrorEdicion(err instanceof Error ? err.message : 'Error al actualizar')
    }
  }

  if (cargando) return <div className="card"><p>Cargando perfil...</p></div>
  if (errorCarga) return <div className="card"><p className="error">{errorCarga}</p></div>
  if (!perfil) return null

  return (
    <div className="card">
      <div className="app-header">
        <h1 className="titulo">Mi Perfil</h1>
      </div>

      {/* VISTA: datos del perfil */}
      {!editando && (
        <div className="perfil-info">
          <div className="perfil-item"><strong>DNI</strong> {perfil.dni}</div>
          <div className="perfil-item"><strong>Nombre</strong> {perfil.nombre} {perfil.apellido}</div>
          <div className="perfil-item"><strong>Mail</strong> {perfil.mail}</div>
          <div className="perfil-item"><strong>Teléfono</strong> {perfil.telefono}</div>

          {successMsg && <span className="success">{successMsg}</span>}

          <button className="btn-agregar" onClick={() => { setSuccessMsg(null); setEditando(true) }}>
            ✏️ Editar perfil
          </button>
          <button className="btn-secundario" onClick={() => navigate(-1)}>
            ← Volver
          </button>
        </div>
      )}

      {/* FORMULARIO: editar teléfono y/o contraseña */}
      {editando && (
        <form className="inputs" onSubmit={handleSubmit(onSubmit)} noValidate>

          <label>Teléfono</label>
          <input
            type="tel"
            className="input-field"
            placeholder="Nuevo teléfono"
            {...register('telefono', {
              required: 'El teléfono es obligatorio',
              minLength: { value: 8, message: 'Teléfono inválido (mínimo 8 dígitos)' },
              pattern: { value: /^\d+$/, message: 'Solo se permiten números' },
            })}
          />
          {errors.telefono && <span className="error">{errors.telefono.message}</span>}

          <label>
            Nueva contraseña&nbsp;
            <span style={{ fontWeight: 'normal', fontSize: '0.85em' }}>(dejá vacío para no cambiarla)</span>
          </label>
          <input
            type="password"
            className="input-field"
            placeholder="Nueva contraseña"
            {...register('contrasenia', {
              minLength: { value: 6, message: 'Mínimo 6 caracteres' },
            })}
          />
          {errors.contrasenia && <span className="error">{errors.contrasenia.message}</span>}

          <label>Confirmar contraseña</label>
          <input
            type="password"
            className="input-field"
            placeholder="Repetí la contraseña"
            {...register('confirmarContrasenia', {
              validate: value =>
                !contraseniaActual || value === contraseniaActual || 'Las contraseñas no coinciden',
            })}
          />
          {errors.confirmarContrasenia && <span className="error">{errors.confirmarContrasenia.message}</span>}

          {errorEdicion && <span className="error">{errorEdicion}</span>}

          <button className="btn-agregar" type="submit">💾 Guardar cambios</button>
          <button
            type="button"
            className="btn-secundario"
            onClick={() => { setEditando(false); setErrorEdicion(null) }}
          >
            Cancelar
          </button>
        </form>
      )}
    </div>
  )
}

export default PerfilPage
