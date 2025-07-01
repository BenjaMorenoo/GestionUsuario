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
import org.springframework.http.MediaType;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Rol rol;
    private Usuario usuario;
    private EntityModel<Usuario> usuarioModel;

    @BeforeEach
    void setUp() {
        rol = new Rol(1, Roles.Profesor, "Imparte clases", new ArrayList<>());
        usuario = new Usuario(1, "Ana", "Lopez", "ana@mail.com", rol);
        usuarioModel = EntityModel.of(usuario);
    }

    @Test
    void testPostUsuario_creaNuevo() throws Exception {
        Usuario nuevo = new Usuario(null, "Ana", "Lopez", "ana@mail.com", rol);
        when(usuarioService.findByXIdUsuario(null)).thenReturn(Optional.empty());
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        when(assembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void testPostUsuario_yaExiste() throws Exception {
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetAllUsuarios_conDatos() throws Exception {
        when(usuarioService.findAllUsuarios()).thenReturn(List.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].idUsuario").value(1));
    }

    @Test
    void testGetAllUsuarios_listaVacia() throws Exception {
        when(usuarioService.findAllUsuarios()).thenReturn(List.of());

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetUsuarioById_encontrado() throws Exception {
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@mail.com"));
    }

    @Test
    void testGetUsuarioById_noEncontrado() throws Exception {
        when(usuarioService.findByXIdUsuario(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuario/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPutUsuario_actualizaCorrecto() throws Exception {
        Usuario actualizado = new Usuario(1, "Elena", "Vera", "elena@mail.com", rol);
        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(actualizado);
        when(assembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(actualizado));

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("elena@mail.com"));
    }

    @Test
    void testPutUsuario_noEncontrado() throws Exception {
        Usuario actualizado = new Usuario(1, "Ana", "Lopez", "nueva@mail.com", rol);
        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(null);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUsuario_encontrado() throws Exception {
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void testDeleteUsuario_noEncontrado() throws Exception {
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isNotFound());
    }
}
