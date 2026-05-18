package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTOs.ClienteDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setDni(12345678);
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Perez");
        clienteDTO.setMail("juan@mail.com");
        clienteDTO.setContrasenia("pass123");
        clienteDTO.setTelefono(1122334455);
        clienteDTO.setDirEntrega("Calle Falsa 123");
        clienteDTO.setCiudad("Buenos Aires");
    }

    // ---- ALTA ----

    @Test
    void altaUsuario_debeGuardarYRetornarUsuario() {
        Usuario guardado = usuarioService.altaUsuario(clienteDTO);

        assertNotNull(guardado);
        assertEquals(12345678, guardado.getDni());
        assertEquals("Juan", guardado.getNombre());
        assertEquals("Perez", guardado.getApellido());
        assertNotNull(((Cliente) guardado).getDirEntrega());
    }

    // ---- MOSTRAR ----

    @Test
    void mostrarUsuario_cuandoExiste_debeRetornarUsuario() {
        usuarioService.altaUsuario(clienteDTO);

        Usuario encontrado = usuarioService.mostrarUsuario(12345678);

        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombre());
        assertEquals("juan@mail.com", encontrado.getMail());
    }

    @Test
    void mostrarUsuario_cuandoNoExiste_debeRetornarNull() {
        Usuario resultado = usuarioService.mostrarUsuario(99999999);

        assertNull(resultado);
    }

    // ---- MODIFICACION ----

    @Test
    void modificarUsuario_debeActualizarDatos() {
        Usuario guardado = usuarioService.altaUsuario(clienteDTO);
        guardado.setNombre("Juan Modificado");

        usuarioService.modificarUsuario(guardado);

        Usuario modificado = usuarioService.mostrarUsuario(12345678);
        assertNotNull(modificado);
        assertEquals("Juan Modificado", modificado.getNombre());
    }

    @Test
    void modificarUsuario_contraseniaPlana_debeQuedarHasheada() {
        usuarioService.altaUsuario(clienteDTO);

        Usuario u = usuarioService.mostrarUsuario(12345678);
        u.setContrasenia("nuevaContrasenia");
        usuarioService.modificarUsuario(u);

        Usuario modificado = usuarioService.mostrarUsuario(12345678);
        assertTrue(modificado.getContrasenia().startsWith("$2a$"),
                "La contraseña debe estar hasheada con BCrypt");
    }

    // ---- PERMISOS (verificados via esAdmin / esPropietario) ----

    @Test
    void esPropietario_mismoUsuario_debeRetornarTrue() {
        usuarioService.altaUsuario(clienteDTO);

        assertTrue(usuarioService.esPropietario("juan@mail.com", 12345678));
    }

    @Test
    void esAdmin_usuarioNoAdmin_debeRetornarFalse() {
        usuarioService.altaUsuario(clienteDTO);

        assertTrue(!usuarioService.esAdmin("juan@mail.com"));
    }

    // ---- BAJA ----

    @Test
    void borrarUsuario_debeEliminarElUsuario() {
        usuarioService.altaUsuario(clienteDTO);

        usuarioService.borrarUsuario(12345678);

        assertNull(usuarioRepository.findById(12345678).orElse(null));
    }

    @Test
    void borrarUsuario_noExiste_debeLanzarExcepcion() {
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> usuarioService.borrarUsuario(99999999));
    }
}
