# Microservicio Gestión de Usuarios

Este proyecto es un microservicio RESTful desarrollado en Spring Boot para la gestión de usuarios y roles en un entorno educativo. Permite crear, editar, eliminar y consultar usuarios y roles, integrando buenas prácticas como HATEOAS y pruebas unitarias.

## Tecnologías principales
- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Springdoc OpenAPI (Swagger)
- JUnit 5 y Mockito (pruebas unitarias)

## Estructura del proyecto

```
├── src/
│   ├── main/
│   │   ├── java/com/msgestionusuario/gestionusuario/
│   │   │   ├── controller/         # Controladores REST
│   │   │   ├── service/            # Lógica de negocio
│   │   │   ├── repository/         # Acceso a datos
│   │   │   ├── model/              # Entidades JPA
│   │   │   └── assemblers/         # HATEOAS assemblers
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/msgestionusuario/gestionusuario/
│           ├── controller/         # Pruebas de controladores
│           └── service/            # Pruebas de servicios
├── pom.xml
└── README.MD
```

## Configuración rápida

1. **Clona el repositorio**
2. Configura tu base de datos MySQL y actualiza `src/main/resources/application.properties` si es necesario.
3. Ejecuta el microservicio:
   ```bash
   ./mvnw spring-boot:run
   ```
   o en Windows:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
4. Accede a la documentación Swagger en: [http://localhost:8081/doc/swagger-ui/index.html#/](http://localhost:8081/doc/swagger-ui/index.html#/)

## Endpoints principales

### Roles
- **GET** `/api/rol` — Lista todos los roles
- **GET** `/api/rol/id={idRol}` — Busca rol por ID
- **GET** `/api/rol/nombreRol={nombreRol}` — Busca rol por nombre
- **POST** `/api/rol` — Crea un nuevo rol
- **PUT** `/api/rol/{idRol}` — Edita un rol existente
- **DELETE** `/api/rol/{idRol}` — Elimina un rol (solo si no tiene usuarios asociados)

#### Ejemplo de JSON para rol
```json
{
  "idRol": 1,
  "nombreRol": "Profesor",
  "funcion": "Imparte clases en distintas asignaturas"
}
```

### Usuarios
- **GET** `/api/usuario` — Lista todos los usuarios
- **GET** `/api/usuario/{idUsuario}` — Busca usuario por ID
- **POST** `/api/usuario` — Crea un nuevo usuario
- **PUT** `/api/usuario/{idUsuario}` — Edita un usuario existente
- **DELETE** `/api/usuario/{idUsuario}` — Elimina un usuario

#### Ejemplo de JSON para usuario
```json
{
  "idUsuario": 1,
  "nombre": "Camila",
  "apellido": "Rojas",
  "email": "camila.rojas@colegio.cl",
  "rol": {
    "idRol": 1
  }
}
```

## Pruebas unitarias

El proyecto incluye pruebas unitarias para servicios y controladores usando JUnit 5 y Mockito. Puedes ejecutar las pruebas con:

```bash
./mvnw test
```

- Las clases de prueba se encuentran en `src/test/java/com/msgestionusuario/gestionusuario/`
- Se utiliza una plantilla de registro de pruebas: [plantilla_registro_pruebas_unitarias_gestionusuarios](https://docs.google.com/spreadsheets/d/1jp5w6ft6WbawjEOXMP701aG_uaoHFPm8z7egTwYmmIo/edit?pli=1&gid=1483674301#gid=1483674301)

## Documentación y referencias
- **Swagger UI:** [http://localhost:8081/doc/swagger-ui/index.html#/](http://localhost:8081/doc/swagger-ui/index.html#/)
- **Plantilla de pruebas:** [Google Sheets](https://docs.google.com/spreadsheets/d/1jp5w6ft6WbawjEOXMP701aG_uaoHFPm8z7egTwYmmIo/edit?pli=1&gid=1483674301#gid=1483674301)

## Notas adicionales
- El microservicio implementa HATEOAS para enriquecer las respuestas con enlaces útiles.
- El campo `email` de usuario es único.
- Un rol solo puede eliminarse si no tiene usuarios asociados.
- El proyecto está preparado para ser extendido fácilmente con nuevos endpoints o lógica de negocio.

---


