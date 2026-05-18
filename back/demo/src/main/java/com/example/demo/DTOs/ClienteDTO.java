package com.example.demo.DTOs;

public class ClienteDTO extends UsuarioDTO {
    private String dirEntrega;
    private String ciudad;

    public String getDirEntrega() {
        return dirEntrega;
    }

    public void setDirEntrega(String dirEntrega) {
        this.dirEntrega = dirEntrega;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}