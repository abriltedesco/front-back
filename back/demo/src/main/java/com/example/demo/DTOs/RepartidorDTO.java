package com.example.demo.DTOs;

public class RepartidorDTO extends UsuarioDTO {
    private boolean disponible;

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}