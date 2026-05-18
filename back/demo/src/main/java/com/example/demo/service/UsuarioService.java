package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.ClienteDTO;
import com.example.demo.DTOs.RepartidorDTO;
import com.example.demo.DTOs.UsuarioDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Repartidor;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario altaUsuario(UsuarioDTO dto) {
        Usuario entidad;

        if (dto instanceof ClienteDTO clienteDTO) {
            Cliente cliente = new Cliente();
            cliente.setDirEntrega(clienteDTO.getDirEntrega());
            cliente.setCiudad(clienteDTO.getCiudad());
            entidad = cliente;
        } else if (dto instanceof RepartidorDTO repartidorDTO) {
            Repartidor repartidor = new Repartidor();
            repartidor.setDisponible(repartidorDTO.isDisponible());
            entidad = repartidor;
        } else {
            entidad = new Usuario();
        }

        entidad.setDni(dto.getDni());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setMail(dto.getMail());
        entidad.setContrasenia(dto.getContrasenia());
        entidad.setTelefono(dto.getTelefono());
        // El tipo lo fija el endpoint, no el DTO — nunca se permite que el usuario elija ADMIN

        return usuarioRepository.save(entidad);
    }

    public void borrarUsuario(int dni) {
        if (!usuarioRepository.existsById(dni)) {
            throw new RuntimeException("No se encontró ningún usuario con DNI " + dni);
        }
        usuarioRepository.deleteById(dni);
    }

    public Usuario modificarUsuario(Usuario usuarioModificado) {
        Usuario existente = usuarioRepository.findById(usuarioModificado.getDni())
                .orElseThrow(() -> new RuntimeException("No se encontró ningún usuario con ese DNI"));

        if (usuarioModificado.getTelefono() != 0) {
            existente.setTelefono(usuarioModificado.getTelefono());
        }

        String nuevaContrasenia = usuarioModificado.getContrasenia();
        if (nuevaContrasenia != null && !nuevaContrasenia.isBlank() && !nuevaContrasenia.startsWith("$2a$")) {
            existente.setContrasenia(passwordEncoder.encode(nuevaContrasenia));
        }

        return usuarioRepository.save(existente);
    }

    public Usuario mostrarUsuario(int dni) {
        return usuarioRepository.findById(dni).orElse(null);
    }

    // Usado por @PreAuthorize en el controller
    public boolean esAdmin(String mail) {
        return usuarioRepository.findByMail(mail).map(u -> "ADMIN".equals(u.getTipo())).orElse(false);
    }

    // Usado por @PreAuthorize en el controller
    public boolean esPropietario(String mail, int dni) {
        return usuarioRepository.findByMail(mail).map(u -> u.getDni() == dni).orElse(false);
    }
}
