package com.msgestionusuario.gestionusuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgestionusuario.gestionusuario.assemblers.UsuarioModelAssembler;
import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol;
    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        rol = new Rol(1, Roles.Profesor, "Imparte clases", new ArrayList<>());
        usuario1 = new Usuario(1, "Juan", "Pérez", "juan@mail.com", rol);
        usuario2 = new Usuario(2, "Ana", "López", "ana@mail.com", rol);
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioService.findAllUsuarios()).thenReturn(usuarios);
        when(assembler.toModel(usuario1)).thenReturn(EntityModel.of(usuario1));
        when(assembler.toModel(usuario2)).thenReturn(EntityModel.of(usuario2));

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Juan"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].nombre").value("Ana"));
    }

    @Test
    void testGetUsuarioById() throws Exception {
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(usuario1));
        when(assembler.toModel(usuario1)).thenReturn(EntityModel.of(usuario1));

        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testCrearUsuario() throws Exception {
        Usuario nuevo = new Usuario(null, "Sofía", "Martínez", "sofia@mail.com", rol);
        Usuario guardado = new Usuario(1, "Sofía", "Martínez", "sofia@mail.com", rol);

        when(usuarioService.findByXIdUsuario(null)).thenReturn(Optional.empty());
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(guardado);
        when(assembler.toModel(guardado)).thenReturn(EntityModel.of(guardado));

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Sofía"));
    }

    @Test
    void testCrearUsuario_conflict() throws Exception {
        Usuario conflictUser = new Usuario(1, "Luis", "Mora", "luis@mail.com", rol);

        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(conflictUser));

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conflictUser)))
                .andExpect(status().isConflict());
    }

    @Test
    void testEditarUsuario() throws Exception {
        Usuario editado = new Usuario(1, "Luis", "González", "luisito@mail.com", rol);
        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(editado);
        when(assembler.toModel(editado)).thenReturn(EntityModel.of(editado));

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("luisito@mail.com"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        Usuario eliminado = new Usuario(1, "Carlos", "Mora", "carlos@mail.com", rol);

        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.of(eliminado));
        when(assembler.toModel(eliminado)).thenReturn(EntityModel.of(eliminado));

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("carlos@mail.com"));
    }

    @Test
    void testEliminarUsuario_noEncontrado() throws Exception {
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isNotFound());
    }
}
