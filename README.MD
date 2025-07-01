### Microservicio Gestion de Usuarios


Roles:
http://localhost:8081/api/rol


{
  "idRol": 1,
  "nombreRol": "Profesor",
  "funcion": "Imparte clases en distintas asignaturas"
}

{
  "idRol": 2,
  "nombreRol": "Alumno",
  "funcion": "Participa en las clases y rinde evaluaciones"
}

Usuarios:
http://localhost:8081/api/usuario

{
  "idUsuario": 1,
  "nombre": "Camila",
  "apellido": "Rojas",
  "email": "camila.rojas@colegio.cl",
  "rol": {
    "idRol": 1
  }
}

{
  "idUsuario": 2,
  "nombre": "Ignacio",
  "apellido": "Muñoz",
  "email": "ignacio.munoz@colegio.cl",
  "rol": {
    "idRol": 2
  }
}



plantilla_registro_pruebas_unitarias_gestionusuarios que esta en este link https://docs.google.com/spreadsheets/d/1jp5w6ft6WbawjEOXMP701aG_uaoHFPm8z7egTwYmmIo/edit?pli=1&gid=1483674301#gid=1483674301 


link del swagger http://localhost:8081/doc/swagger-ui/index.html#/
