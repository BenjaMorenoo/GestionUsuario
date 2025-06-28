package com.msgestionusuario.gestionusuario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.msgestionusuario.gestionusuario.assemblers.UsuarioModelAssembler;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.service.UsuarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> postUsuario(@RequestBody Usuario usuario) {
        Usuario buscado = usuarioService.findByXIdUsuario(usuario.getIdUsuario()).orElse(null);
        if (buscado == null) {
            Usuario creado = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>(assembler.toModel(creado), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getUsuario() {
        List<Usuario> lista = usuarioService.findAllUsuarios();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EntityModel<Usuario>> usuarios = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                CollectionModel.of(usuarios, linkTo(methodOn(UsuarioController.class).getUsuario()).withSelfRel()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioXid(@PathVariable Integer idUsuario) {
        Usuario buscado = usuarioService.findByXIdUsuario(idUsuario).orElse(null);
        if (buscado == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(assembler.toModel(buscado), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<EntityModel<Usuario>> putUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.editUsuario(idUsuario, usuario);
        if (actualizado != null) {
            return new ResponseEntity<>(assembler.toModel(actualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<EntityModel<Usuario>> deleteUsuario(@PathVariable Integer idUsuario) {
        Usuario eliminado = usuarioService.eliminarUsuario(idUsuario).orElse(null);
        if (eliminado != null) {
            return new ResponseEntity<>(assembler.toModel(eliminado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
