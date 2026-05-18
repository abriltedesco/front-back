import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import { registrarUsuario } from '../service/autenticacion'
import type { Usuario } from '../interfaces/Usuario'

function FormReg() {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<Usuario>()
  const navigate = useNavigate()
  const [errorMsg, setErrorMsg] = useState<string | null>(null)
  const [successMsg, setSuccessMsg] = useState<string | null>(null)

  const onSubmit = async (data: Usuario) => {
    try {
      setErrorMsg(null)
      await registrarUsuario(data)
      setSuccessMsg("¡Registro exitoso! Ya podés iniciar sesión.")
      reset()
      navigate('/login')
    } catch (error) {
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
        <label>DNI</label>
        <input type="text" inputMode="numeric" className="input-field" placeholder="Ingresa tu DNI"
          {...register('dni', {
            required: 'El DNI es obligatorio',
            valueAsNumber: true,
            min: { value: 1000000, message: 'DNI inválido (mínimo 7 dígitos)' },
            max: { value: 99999999, message: 'DNI inválido (máximo 8 dígitos)' },
          })}
        />
        {errors.dni && <span className="error">{errors.dni.message}</span>}

        <label>Nombre</label>
        <input type="text"  className="input-field" placeholder="Ingresa tu nombre"
          {...register('nombre', { required: 'El nombre es obligatorio' })}
        />
        {errors.nombre && <span className="error">{errors.nombre.message}</span>}

        <label>Apellido</label>
        <input  type="text" className="input-field" placeholder="Ingresa tu apellido"
          {...register('apellido', { required: 'El apellido es obligatorio' })}
        />
        {errors.apellido && <span className="error">{errors.apellido.message}</span>}

        <label>Mail</label>
        <input type="email" className="input-field" placeholder="Ingresa tu mail"  {...register('mail', {
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
            required: 'La contraseña es obligatoria',
            minLength: { value: 6, message: 'Mínimo 6 caracteres' },
          })}
        />
        {errors.contrasenia && <span className="error">{errors.contrasenia.message}</span>}

        <label>Teléfono</label>
        <input
          type="tel"
          className="input-field"
          placeholder="Ingresa tu teléfono"
          {...register('telefono', {
            required: 'El teléfono es obligatorio',
            minLength: { value: 8, message: 'Teléfono inválido' },
          })}
        />
        {errors.telefono && <span className="error">{errors.telefono.message}</span>}

        {errorMsg && <span className="error">{errorMsg}</span>}
        {successMsg && <span className="success">{successMsg}</span>}
        <button className="btn-agregar" type="submit">Registrarse</button>
        <p>Si ya tenés una cuenta.. <a href="/login">Iniciá sesión</a></p>
      </form>
    </div>
  )
}

export default FormReg
