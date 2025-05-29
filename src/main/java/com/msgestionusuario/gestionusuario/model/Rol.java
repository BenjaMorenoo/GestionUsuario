package com.msgestionusuario.gestionusuario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")


public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Enumerated(EnumType.STRING)
    private Roles nombreRol;

    @Column(length = 50, nullable = false)
    private String funcion;

    @OneToMany(mappedBy = "rol") // relacion de uno a muchos
    @JsonManagedReference

    private List<Usuario> usuarios;
}
