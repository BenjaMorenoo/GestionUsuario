package com.msgestionusuario.gestionusuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol(1, Roles.Profesor, "Imparte clases", new ArrayList<>());
        
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1, "Juan", "Pérez", "juan@mail.com", rol),
                new Usuario(2, "Ana", "López", "ana@mail.com", rol)
        );

        when(usuarioService.findAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("Ana"));
    }

    @Test
    void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario(1, "Luis", "González", "luis@mail.com", rol);
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }

    @Test
    void testCrearUsuario() throws Exception {
        Usuario nuevo = new Usuario(0, "Sofía", "Martínez", "sofia@mail.com", rol);
        Usuario guardado = new Usuario(1, "Sofía", "Martínez", "sofia@mail.com", rol);

        when(usuarioService.findByXIdUsuario(null)).thenReturn(Optional.empty());
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.nombre").value("Sofía"));
    }

    @Test
    void testEditarUsuario() throws Exception {
        Usuario editado = new Usuario(1, "Luis", "González", "luisito@mail.com", rol);

        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(editado);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("luisito@mail.com"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        Usuario usuario = new Usuario(1, "Carlos", "Mora", "carlos@mail.com", rol);
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("carlos@mail.com"));
    }
}
