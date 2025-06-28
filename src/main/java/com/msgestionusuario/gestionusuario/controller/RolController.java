package com.msgestionusuario.gestionusuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.msgestionusuario.gestionusuario.assemblers.RolModelAssembler;
import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.service.RolService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Rol>> postRol(@RequestBody Rol rol) {
        Rol buscado = rolService.findxIdRol(rol.getIdRol());
        if (buscado == null) {
            Rol creado = rolService.crearRol(rol);
            return new ResponseEntity<>(assembler.toModel(creado), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> getRol() {
        List<Rol> lista = rolService.findAllRoles();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EntityModel<Rol>> roles = lista.stream()
                .map(assembler::toModel)
                .toList();

        return new ResponseEntity<>(CollectionModel.of(
                roles,
                linkTo(methodOn(RolController.class).getRol()).withSelfRel()
        ), HttpStatus.OK);
    }

    @GetMapping("/id={idRol}")
    public ResponseEntity<EntityModel<Rol>> getRolxId(@PathVariable Integer idRol) {
        Rol rol = rolService.findxIdRol(idRol);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(assembler.toModel(rol), HttpStatus.OK);
    }

    @GetMapping("/nombreRol={nombreRol}")
    public ResponseEntity<EntityModel<Rol>> getRolxNombreRol(@PathVariable String nombreRol) {
        Rol rol = rolService.findxNombreRol(nombreRol);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(assembler.toModel(rol), HttpStatus.OK);
    }

    @PutMapping("/{idRol}")
    public ResponseEntity<EntityModel<Rol>> putRol(@PathVariable Integer idRol, @RequestBody Rol rol) {
        Rol actualizado = rolService.editRol(idRol, rol);
        if (actualizado != null) {
            return new ResponseEntity<>(assembler.toModel(actualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<EntityModel<Rol>> deleteRol(@PathVariable Integer idRol) {
        Rol eliminado = rolService.eliminarRol(idRol);
        if (eliminado != null) {
            return new ResponseEntity<>(assembler.toModel(eliminado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
