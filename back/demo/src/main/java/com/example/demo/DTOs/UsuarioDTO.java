package com.example.demo.DTOs;

public class UsuarioDTO {
    private int dni;
    private String nombre;
    private String apellido;
    private String mail;
    private String contrasenia;
    private int telefono;
    private String tipo;

    public int getDni() { 
        return dni; 
    }
    public void setDni(int dni) { 
        this.dni = dni; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getApellido() { 
        return apellido; 
    }
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

    public String getMail() { 
        return mail; 
    }
    public void setMail(String mail) { 
        this.mail = mail; 
    }

    public String getContrasenia() { 
        return contrasenia; 
    }
    public void setContrasenia(String contrasenia) { 
        this.contrasenia = contrasenia; 
    }
    public int getTelefono() { 
        return telefono; 
    }
    public void setTelefono(int telefono) { 
        this.telefono = telefono; 
    }
    public String getTipo() { 
            return tipo; 
    }
    public void setTipo(String tipo) { 
            this.tipo = tipo; 
    }   
}
