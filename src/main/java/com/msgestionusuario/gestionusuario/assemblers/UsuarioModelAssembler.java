package com.msgestionusuario.gestionusuario.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.controller.UsuarioController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{
    
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuario()).withRel("all-usuarios"),
                linkTo(methodOn(UsuarioController.class).getUsuarioXid(usuario.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).deleteUsuario(usuario.getIdUsuario())).withRel("delete")
        );
    }
    
}
