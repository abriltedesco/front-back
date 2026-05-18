package com.example.demo.DTOs;

import com.example.demo.entity.Repartidor;
import com.example.demo.entity.Usuario;

public class PerfilDTO {

    private int dni;
    private String nombre;
    private String apellido;
    private String mail;
    private int telefono;
    private String tipo;

    // Solo presente si es Repartidor
    private Boolean disponible;

    public PerfilDTO(Usuario usuario) {
        this.dni = usuario.getDni();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.mail = usuario.getMail();
        this.telefono = usuario.getTelefono();
        this.tipo = usuario.getTipo();

        if (usuario instanceof Repartidor repartidor) {
            this.disponible = repartidor.isDisponible();
        }
    }

    public int getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getMail() { return mail; }
    public int getTelefono() { return telefono; }
    public String getTipo() { return tipo; }
    public Boolean getDisponible() { return disponible; }
}
