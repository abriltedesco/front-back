package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.PerfilDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "ABM de usuarios (requiere token JWT)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Modificar un usuario (solo el propio usuario o un admin)")
    @PostMapping("/modificar")
    @PreAuthorize("@usuarioService.esPropietario(authentication.name, #usuario.dni) or @usuarioService.esAdmin(authentication.name)")
    public ResponseEntity<String> modificarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.modificarUsuario(usuario);
            return ResponseEntity.ok("Usuario modificado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Borrar un usuario por DNI (solo el propio usuario o un admin)")
    @DeleteMapping("/borrar/{dni}")
    @PreAuthorize("@usuarioService.esPropietario(authentication.name, #dni) or @usuarioService.esAdmin(authentication.name)")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int dni) {
        try {
            usuarioService.borrarUsuario(dni);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener un usuario por DNI")
    @GetMapping("/mostrar/{dni}")
    @PreAuthorize("@usuarioService.esPropietario(authentication.name, #dni) or @usuarioService.esAdmin(authentication.name)")
    public ResponseEntity<?> mostrarUsuario(@PathVariable int dni) {
        try {
            Usuario usuario = usuarioService.mostrarUsuario(dni);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún usuario con ese DNI");
            }
            return ResponseEntity.ok(new PerfilDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
