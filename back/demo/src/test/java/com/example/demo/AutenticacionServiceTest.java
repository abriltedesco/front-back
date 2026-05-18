package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTOs.ClienteDTO;
import com.example.demo.DTOs.LogInDTO;
import com.example.demo.service.AutenticacionService;

@SpringBootTest
@Transactional
public class AutenticacionServiceTest {

    @Autowired
    private AutenticacionService autenticacionService;

    private ClienteDTO clienteDTO;
    private LogInDTO loginDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setDni(87654321);
        clienteDTO.setNombre("Maria");
        clienteDTO.setApellido("Lopez");
        clienteDTO.setMail("maria@mail.com");
        clienteDTO.setContrasenia("clave123");
        clienteDTO.setDirEntrega("Av. Siempreviva 742");
        clienteDTO.setCiudad("Córdoba");

        loginDTO = new LogInDTO();
        loginDTO.setEmail("maria@mail.com");
        loginDTO.setContrasenia("clave123");
    }

    // ---- REGISTRO ----

    @Test
    void registrarUsuario_emailNuevo_debeGuardarSinExcepcion() {
        assertDoesNotThrow(() -> autenticacionService.registrarUsuario(clienteDTO));
    }

    @Test
    void registrarUsuario_emailDuplicado_debeLanzarExcepcion() {
        autenticacionService.registrarUsuario(clienteDTO);

        ClienteDTO duplicado = new ClienteDTO();
        duplicado.setDni(11111111);
        duplicado.setNombre("Otro");
        duplicado.setApellido("Usuario");
        duplicado.setMail("maria@mail.com"); // mismo mail
        duplicado.setContrasenia("otraPass");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autenticacionService.registrarUsuario(duplicado));

        assertTrue(ex.getMessage().contains("datos ya están en uso"));
    }

    // ---- LOGIN ----

    @Test
    void login_credencialesCorrectas_debeRetornarTokenNoNulo() {
        autenticacionService.registrarUsuario(clienteDTO);

        String token = autenticacionService.loginCliente(loginDTO);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void login_usuarioNoExiste_debeLanzarExcepcion() {
        loginDTO.setEmail("noexiste@mail.com");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autenticacionService.loginCliente(loginDTO));

        assertNotNull(ex.getMessage());
        assertFalse(ex.getMessage().isBlank());
    }

    @Test
    void login_contrasenaIncorrecta_debeLanzarExcepcion() {
        autenticacionService.registrarUsuario(clienteDTO);

        loginDTO.setContrasenia("contraseniaErronea");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autenticacionService.loginCliente(loginDTO));

        assertNotNull(ex.getMessage());
        assertFalse(ex.getMessage().isBlank());
    }
}
