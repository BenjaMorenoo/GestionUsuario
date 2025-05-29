package com.msgestionusuario.gestionusuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msgestionusuario.gestionusuario.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /*
        +Usuario crearUsuario(Usuario: usuario)
        +Usuario editarUsuario(Usuario: usuario)
        +eliminarUsuario(String idUsuario)
        +List<Usuario> encontrarUsuarios()
     */
    
    
    Usuario save(Usuario usuario);
    //Usuario findById(int idUsuario);
    List<Usuario> findAll();
    Usuario deleteById(int idUsuario);
    
}
