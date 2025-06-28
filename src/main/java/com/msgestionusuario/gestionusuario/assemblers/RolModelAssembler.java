package com.msgestionusuario.gestionusuario.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.msgestionusuario.gestionusuario.controller.RolController;
import com.msgestionusuario.gestionusuario.model.Rol;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {

    @Override
    public EntityModel<Rol> toModel(Rol rol) {
        return EntityModel.of(rol,
                linkTo(methodOn(RolController.class).getRolxId(rol.getIdRol())).withSelfRel(),
                linkTo(methodOn(RolController.class).getRol()).withRel("all-roles"),
                linkTo(methodOn(RolController.class).deleteRol(rol.getIdRol())).withRel("delete"));
    }
}