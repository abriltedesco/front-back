
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useNavigate, Navigate } from 'react-router-dom'
import { loginUsuario, identificaRol } from '../service/autenticacion'
import { useAuth } from '../context/AuthContext'

type FormLogInData = {
  mail: string
  contrasenia: string
}

function FormLogIn() {
  const { register, handleSubmit, formState: { errors } } = useForm<FormLogInData>()
  const navigate = useNavigate()
  const auth = useAuth()
  const [errorMsg, setErrorMsg] = useState<string | null>(null)

  // Si ya está logueado, redirige directo al home sin mostrar el formulario
  if (auth.token) {
    const rol = identificaRol(auth.token)
    if (rol === 'ADMIN') return <Navigate to="/admin" replace />
    if (rol === 'REPARTIDOR') return <Navigate to="/repartidor" replace />
    return <Navigate to="/home" replace />
  }

  const onSubmit = async (data: FormLogInData) => {
    try {
      setErrorMsg(null)
      // loginUsuario devuelve el token JWT que llegó del backend
      const token = await loginUsuario(data.mail, data.contrasenia)
      auth.login(token) // (guarda token + rol en el estado de React)

      const rol = identificaRol(token)
      if (rol === 'ADMIN') navigate('/admin')
      else if (rol === 'REPARTIDOR') navigate('/repartidor')
      else navigate('/home')
    } 
    catch (error) {
      const msg = error instanceof Error ? error.message : "Error"
      setErrorMsg(msg)
    }
  }

  return (
    <div className="card">
      <div className="app-header">
        <h1 className="titulo">BIENVENIDO A COMIDAPP !</h1>
      </div>

      <form className="inputs" onSubmit={handleSubmit(onSubmit)} noValidate>
        <label>Mail</label>
        <input
          type="email"
          className="input-field"
          placeholder="Ingresa tu mail"
          {...register('mail', {
            required: 'El mail es obligatorio',
            pattern: { value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Mail inválido' },
          })}
        />
        {errors.mail && <span className="error">{errors.mail.message}</span>}

        <label>Contraseña</label>
        <input
          type="password"
          className="input-field"
          placeholder="Ingresa tu contraseña"
          {...register('contrasenia', {
            required: 'La contraseña es obligatoria'   })}
        />
        {errors.contrasenia && <span className="error">{errors.contrasenia.message}</span>}

        {errorMsg && <span className="error">{errorMsg}</span>}
        <button className="btn-agregar" type="submit">Iniciar sesión</button>
        <p>Si no tenés una cuenta.. <a href="/register">Registrate</a></p>
      </form>
    </div>
  )
}

export default FormLogIn
