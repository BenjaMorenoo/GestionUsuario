package com.msgestionusuario.gestionusuario.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgestionusuario.gestionusuario.assemblers.RolModelAssembler;
import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.service.RolService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @MockBean
    private RolModelAssembler assembler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Rol rol;
    private EntityModel<Rol> rolModel;

    @BeforeEach
    void setUp() {
        rol = new Rol(1, Roles.Profesor, "Impartir clases", null);
        rolModel = EntityModel.of(rol); // Modelo simulado con HATEOAS
    }

    @Test
    void testPostRol_creaNuevo() throws Exception {
        when(rolService.findxIdRol(1)).thenReturn(null);
        when(rolService.crearRol(any(Rol.class))).thenReturn(rol);
        when(assembler.toModel(any(Rol.class))).thenReturn(rolModel);

        mockMvc.perform(post("/api/rol")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRol").value(1));
    }

    @Test
    void testPostRol_yaExiste() throws Exception {
        when(rolService.findxIdRol(1)).thenReturn(rol);

        mockMvc.perform(post("/api/rol")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetRol_listaConDatos() throws Exception {
        when(rolService.findAllRoles()).thenReturn(List.of(rol));
        when(assembler.toModel(any(Rol.class))).thenReturn(rolModel);

        mockMvc.perform(get("/api/rol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.rolList[0].idRol").value(1));
    }

    @Test
    void testGetRol_listaVacia() throws Exception {
        when(rolService.findAllRoles()).thenReturn(List.of());

        mockMvc.perform(get("/api/rol"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetRolxId_encontrado() throws Exception {
        when(rolService.findxIdRol(1)).thenReturn(rol);
        when(assembler.toModel(any(Rol.class))).thenReturn(rolModel);

        mockMvc.perform(get("/api/rol/id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1));
    }

    @Test
    void testGetRolxId_noEncontrado() throws Exception {
        when(rolService.findxIdRol(1)).thenReturn(null);

        mockMvc.perform(get("/api/rol/id=1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetRolxNombre_encontrado() throws Exception {
        when(rolService.findxNombreRol("Profesor")).thenReturn(rol);
        when(assembler.toModel(any(Rol.class))).thenReturn(rolModel);

        mockMvc.perform(get("/api/rol/nombreRol=Profesor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("Profesor"));
    }

    @Test
    void testGetRolxNombre_noEncontrado() throws Exception {
        when(rolService.findxNombreRol("ADMINISTRADOR")).thenReturn(null);

        mockMvc.perform(get("/api/rol/nombreRol=ADMINISTRADOR"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRol_eliminadoCorrecto() throws Exception {
        when(rolService.eliminarRol(1)).thenReturn(rol);
        when(assembler.toModel(any(Rol.class))).thenReturn(rolModel);

        mockMvc.perform(delete("/api/rol/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1));
    }

    @Test
    void testDeleteRol_noEncontrado() throws Exception {
        when(rolService.eliminarRol(1)).thenReturn(null);

        mockMvc.perform(delete("/api/rol/1"))
                .andExpect(status().isNotFound());
    }
}
