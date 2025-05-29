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

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.service.RolService;

@RestController
@RequestMapping("/api/rol")

public class RolController {
    
    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> postRol(@RequestBody Rol rol) {
        Rol buscado = rolService.findxIdRol(rol.getIdRol());
        if (buscado == null) {
            return new ResponseEntity<>(rolService.crearRol(rol), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
/* 
    @PostMapping("/crearRol")
    public ResponseEntity<Rol> postXnombreRol(@RequestBody Rol rol){
        Rol buscado = rolService.findxNombreRol(rol.getNombreRol().toString());
        if (buscado == null){
            return new ResponseEntity<>(rolService.crearRolxNombreRol(rol), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
*/
    
    @GetMapping
    public ResponseEntity<List<Rol>> getRol() {
        List<Rol> lista = rolService.findAllRoles();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/id={idRol}")
    public ResponseEntity<Rol> getRolxId(@PathVariable Integer idRol) {
        Rol rol = rolService.findxIdRol(idRol);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(rol, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/nombreRol={nombreRol}")
    public ResponseEntity<Rol> getRolxNombreRol(@PathVariable String nombreRol) {
        Rol rol = rolService.findxNombreRol(nombreRol);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(rol, HttpStatus.ACCEPTED);
        }
    }
    
    @PutMapping("/{idRol}")
    public ResponseEntity<Rol> putRol(@RequestBody Rol rol) {
        Rol actualizado = rolService.editRol(rol.getIdRol(), rol);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<Rol> deleteRol(@PathVariable Integer idRol) {
        Rol eliminado = rolService.eliminarRol(idRol);
        if (eliminado != null) {
            return new ResponseEntity<>(eliminado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
