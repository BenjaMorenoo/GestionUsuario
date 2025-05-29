package com.msgestionusuario.gestionusuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.repository.UsuarioRepository;

@Service


public class UsuarioService {
  
    
      /*
       +Usuario crearUsuario(Usuario: usuario)
        +Usuario editarUsuario(Usuario: usuario)
        + eliminarUsuario(String idUsuario)
        +List<Usuario> encontrarUsuarios()
       */

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByXIdUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }
    

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }
    
    public Usuario editUsuario(Integer idUsuario, Usuario usuario){
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
        if (usuarioExistente.isPresent()) {
            Usuario usuarioActualizado = usuarioExistente.get();
            usuarioActualizado.setNombre(usuario.getNombre());
            usuarioActualizado.setApellido(usuario.getApellido());
            usuarioActualizado.setEmail(usuario.getEmail());
            usuarioActualizado.setRol(usuario.getRol()); // añadir esta parte para poder daler el rol al usuario


            return usuarioRepository.save(usuarioActualizado);
        }
        return null;
    }

    public Optional<Usuario> eliminarUsuario(int idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario != null) { // ⚠️ Aquí debería ser usuario.isPresent()
            usuarioRepository.deleteById(idUsuario);
            return usuario;
        }
        return Optional.empty(); // ⚠️ Debes devolver Optional.empty() en lugar de null
    }

    
}

