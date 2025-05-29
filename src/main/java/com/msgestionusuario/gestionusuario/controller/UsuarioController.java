package com.msgestionusuario.gestionusuario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    
    @PostMapping
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
        Usuario buscado = usuarioService.findByXIdUsuario(usuario.getIdUsuario()).orElse(null);
        if (buscado == null) {
            return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        List<Usuario> lista = usuarioService.findAllUsuarios();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        }
    }


    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> putUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.editUsuario(idUsuario, usuario);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Integer idUsuario) {
        Usuario eliminado = usuarioService.eliminarUsuario(idUsuario).orElse(null);;
        if (eliminado != null) {
            return new ResponseEntity<>(eliminado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> getUsuarioXid(@PathVariable Integer idUsuario) {
        Usuario buscado=usuarioService.findByXIdUsuario(idUsuario).orElse(null); //.orElse controla el tipo Optional<Cliente>
        if(buscado==null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buscado,HttpStatus.OK);
    }
    

}
