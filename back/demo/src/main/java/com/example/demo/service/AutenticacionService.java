package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.LogInDTO;
import com.example.demo.DTOs.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.ConflictoExc;
import com.example.demo.exception.CredencialesInvalidasException;
import com.example.demo.repository.UsuarioRepository;

@Service
public class AutenticacionService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder definido en SecurityConfig
    public String loginCliente(LogInDTO login) {
        Usuario usuario = usuarioRepository.findByMail(login.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException());

        // Comparar con BCrypt en lugar de texto plano
        if (!passwordEncoder.matches(login.getContrasenia(), usuario.getContrasenia())) {
            throw new CredencialesInvalidasException();
        }

        return jwtService.generarToken(usuario.getMail(), usuario.getTipo(), usuario.getDni());
    }

    // Registro de nuevo usuario — guarda la contraseña encriptada
    public void registrarUsuario(UsuarioDTO datosUsuario) {
        if (usuarioRepository.findByMail(datosUsuario.getMail()).isPresent()
                || usuarioRepository.findById(datosUsuario.getDni()).isPresent()) {
            throw new ConflictoExc("Los datos ya están en uso");
        }

        // Encriptar contraseña antes de persistir
        datosUsuario.setContrasenia(passwordEncoder.encode(datosUsuario.getContrasenia()));

        usuarioService.altaUsuario(datosUsuario);
    }
}