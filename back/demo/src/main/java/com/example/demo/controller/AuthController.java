package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.ClienteDTO;
import com.example.demo.DTOs.LogInDTO;
import com.example.demo.DTOs.RepartidorDTO;
import com.example.demo.exception.ConflictoExc;
import com.example.demo.exception.CredencialesInvalidasException;
import com.example.demo.service.AutenticacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Registro y login de usuarios")
public class AuthController {

    @Autowired
    private AutenticacionService autenticacionService;

    @Operation(summary = "Registrar un cliente", description = "Crea una nueva cuenta de tipo Cliente")
    @PostMapping("/registro/cliente")
    public ResponseEntity<String> registrarCliente(@RequestBody ClienteDTO datosCliente) {
        try {
            autenticacionService.registrarUsuario(datosCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente registrado correctamente");
        } catch (ConflictoExc e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al registrar el cliente");
        }
    }

    @Operation(summary = "Registrar un repartidor", description = "Crea una nueva cuenta de tipo Repartidor")
    @PostMapping("/registro/repartidor")
    public ResponseEntity<String> registrarRepartidor(@RequestBody RepartidorDTO datosRepartidor) {
        try {
            autenticacionService.registrarUsuario(datosRepartidor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Repartidor registrado correctamente");
        } catch (ConflictoExc e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al registrar el repartidor");
        }
    }

    @Operation(summary = "Login", description = "Devuelve un token JWT si las credenciales son correctas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogInDTO login) {
        try {
            String token = autenticacionService.loginCliente(login);
            return ResponseEntity.ok(token);
        } catch (CredencialesInvalidasException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al iniciar sesión");
        }
    }
}



