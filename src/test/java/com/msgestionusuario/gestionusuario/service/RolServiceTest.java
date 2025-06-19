package com.msgestionusuario.gestionusuario.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import java.util.Optional;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.repository.RolRepository;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    void setUp() {
        // Aquí puedes inicializar los mocks y cualquier otra configuración necesaria
        // Por ejemplo, usando MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(this);
    }

    // aqui se agregan los test unitarios para RolService

    @Test
    void testCrearRol() {
        Rol rol = new Rol(0, Roles.Profesor, "Impartir clases", null);
        Rol rolGuardado = new Rol(1, Roles.Profesor, "Impartir clases", null);
        // Simula el comportamiento del repositorio
        when(rolRepository.save(rol)).thenReturn(rolGuardado);

        // Llama al método que se está probando
        Rol resultado = rolService.crearRol(rol);
        assertThat(resultado.getIdRol()).isEqualTo(1L);
        verify(rolRepository).save(rol);

    }

    @Test
    void testFindxNombreRol() {
        Rol rol = new Rol(1, Roles.Profesor, "Impartir clases", null);
        when(rolRepository.findByNombreRol(Roles.Profesor)).thenReturn(rol);

        Rol resultado = rolService.findxNombreRol("Profesor");

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreRol()).isEqualTo(Roles.Profesor);
        verify(rolRepository).findByNombreRol(Roles.Profesor);
    }

    @Test
    void testFindxIdRol() {
        Rol rol = new Rol(1, Roles.Profesor, "Impartir clases", null);
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        Rol resultado = rolService.findxIdRol(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdRol()).isEqualTo(1);
        verify(rolRepository).findById(1);
    }

    @Test
    void testFindAllRoles() {
        List<Rol> roles = List.of(
                new Rol(1, Roles.Profesor, "Impartir clases", null),
                new Rol(2, Roles.Alumno, "Asistir a clases", null));
        when(rolRepository.findAll()).thenReturn(roles);

        List<Rol> resultado = rolService.findAllRoles();

        assertThat(resultado).hasSize(2);
        verify(rolRepository).findAll();
    }

    @Test
    void testEditRol() {
        Rol existente = new Rol(1, Roles.Profesor, "Impartir clases", null);
        Rol nuevo = new Rol(1, Roles.Profesor, "Impartir evaluaciones", null);

        when(rolRepository.findById(1)).thenReturn(Optional.of(existente));
        when(rolRepository.save(any(Rol.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rol resultado = rolService.editRol(1, nuevo);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreRol()).isEqualTo(Roles.Profesor);
        assertThat(resultado.getFuncion()).isEqualTo("Impartir evaluaciones");
        verify(rolRepository).save(any(Rol.class));
    }

    @Test
    void testEliminarRol_SinUsuarios() {
        Rol rol = new Rol(1, Roles.Profesor, "Impartir clases", null);
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        Rol resultado = rolService.eliminarRol(1);

        assertThat(resultado).isNotNull();
        verify(rolRepository).deleteById(1);
    }

    @Test
    void testEliminarRol_ConUsuarios() {
        Rol rol = new Rol(1, Roles.Profesor, "Impartir clases", List.of(new Usuario()));
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        Rol resultado = rolService.eliminarRol(1);

        assertThat(resultado).isNull();
        verify(rolRepository, never()).deleteById(anyInt());
    }
}
