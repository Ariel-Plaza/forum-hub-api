# 🗣️ Forum Hub API

REST API para la administración de un sistema de foro, desarrollada con **Spring Boot 3** y **Java 17**. Permite gestionar tópicos con autenticación segura basada en **JWT** y arquitectura **stateless**.

---

## 🚀 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.5.10 | Framework base |
| Spring Security | — | Seguridad stateless con filtro JWT personalizado |
| Spring Data JPA | — | Persistencia y acceso a datos |
| MySQL | — | Base de datos relacional |
| Flyway | — | Migraciones de base de datos |
| Auth0 Java JWT | 4.2.1 | Generación y validación de tokens JWT |
| Lombok | — | Reducción de código boilerplate |
| dotenv-java | 3.0.0 | Manejo de variables de entorno |
| Maven | — | Gestión de dependencias y build |

---

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- MySQL 8+
- Un cliente HTTP como [Insomnia](https://insomnia.rest/) o [Postman](https://www.postman.com/)

---

## ⚙️ Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/Ariel-Plaza/forum-hub-api.git
cd forum-hub-api
```

### 2. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```env
DB_HOST=localhost:3306
DB_NAME=forumhub
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
JWT_SECRET=tu_clave_secreta_jwt
```

> Si `JWT_SECRET` no está definida, el sistema usará `12345678` como valor por defecto. **No usar en producción.**

### 3. Crear la base de datos en MySQL

```sql
CREATE DATABASE forumhub;
```

> Las migraciones de tablas se ejecutan automáticamente al iniciar la aplicación gracias a **Flyway**.

### 4. Ejecutar el proyecto

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

---

## 🔐 Seguridad

La API implementa seguridad **stateless** con Spring Security + JWT:

- Las sesiones **no se almacenan** en el servidor (`SessionCreationPolicy.STATELESS`)
- Cada petición es autenticada mediante un **filtro personalizado** (`SecurityFilter`) que valida el token JWT del header `Authorization`
- Las contraseñas se almacenan hasheadas con **BCrypt**
- Todos los usuarios reciben el rol `ROLE_USER` por defecto

### Endpoints públicos (sin autenticación)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/login` | Obtener token JWT |
| `GET` | `/v3/api-docs/**` | Documentación OpenAPI |
| `GET` | `/swagger-ui/**` | Interfaz Swagger UI |

### Flujo de autenticación

```
Cliente → POST /login → Token JWT ← API
Cliente → [cualquier endpoint] + Header "Authorization: Bearer <token>" → API
```

---

## 📡 Endpoints

### 🔑 `POST /login` — Autenticarse

**Request body:**

```json
{
  "login": "usuario@email.com",
  "clave": "tu_contraseña"
}
```

**Respuesta `200 OK`:**

```json
{
  "jwTtoken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 💬 Tópicos — Todos requieren autenticación

#### `POST /topicos` — Crear tópico

Todos los campos son **obligatorios** (`@NotBlank`). `titulo` y `mensaje` deben ser **únicos** en la base de datos.

```json
{
  "titulo": "¿Cómo usar Spring Security con JWT?",
  "mensaje": "Estoy intentando implementar autenticación JWT en mi API...",
  "status": "ABIERTO",
  "autor": "Ariel",
  "curso": "Spring Boot"
}
```

**Respuesta `201 Created`** + header `Location: /topicos/{id}`:

```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security con JWT?",
  "mensaje": "Estoy intentando implementar autenticación JWT en mi API...",
  "fechaCreacion": "2025-02-23T14:30:00",
  "status": "ABIERTO",
  "autor": "Ariel",
  "curso": "Spring Boot"
}
```

---

#### `GET /topicos` — Listar tópicos activos

Solo retorna tópicos con `activo = true`. Soporta paginación con los siguientes parámetros personalizados:

| Parámetro | Descripción | Default |
|-----------|-------------|---------|
| `pagina` | Número de página | `0` |
| `tamano` | Elementos por página | `10` |
| `orden` | Campo de ordenamiento | `fechaCreacion,asc` |

```
GET /topicos?pagina=0&tamano=10&orden=fechaCreacion,asc
```

**Respuesta `200 OK`** — Nota: el listado **no incluye el campo `id`**:

```json
{
  "content": [
    {
      "titulo": "¿Cómo usar Spring Security con JWT?",
      "mensaje": "Estoy intentando implementar autenticación JWT en mi API...",
      "fechaCreacion": "2025-02-23T14:30:00",
      "status": "ABIERTO",
      "autor": "Ariel",
      "curso": "Spring Boot"
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0
}
```

---

#### `GET /topicos/{id}` — Detalle de un tópico

```
GET /topicos/1
```

**Respuesta `200 OK`:**

```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security con JWT?",
  "mensaje": "Estoy intentando implementar autenticación JWT en mi API...",
  "fechaCreacion": "2025-02-23T14:30:00",
  "status": "ABIERTO",
  "autor": "Ariel",
  "curso": "Spring Boot"
}
```

---

#### `PUT /topicos/{id}` — Actualizar tópico

El campo `id` es **obligatorio** (`@NotNull`). `titulo` y `mensaje` son obligatorios si se envían (`@NotBlank`). El resto de los campos son opcionales y solo se actualizan si se incluyen.

```json
{
  "id": 1,
  "titulo": "Título actualizado",
  "status": "CERRADO"
}
```

**Respuesta `200 OK`:** retorna el tópico completo con los datos actualizados.  
**Respuesta `404 Not Found`:** si el ID no existe.

---

#### `DELETE /topicos/{id}` — Eliminar tópico

Realiza un **borrado lógico**: cambia `activo` a `false` sin eliminar el registro físicamente de la base de datos.

**Respuesta `204 No Content`:** si el tópico existe y fue desactivado.  
**Respuesta `404 Not Found`:** si el ID no existe.

---

## 🗂️ Modelo de Datos

### Usuario (`usuarios`)

| Campo | Tipo | Restricciones |
|-------|------|---------------|
| `id` | `BIGINT` | PK, autogenerado |
| `login` | `VARCHAR` | Único, email o username |
| `clave` | `VARCHAR` | Hash BCrypt |

### Tópico (`topicos`)

| Campo | Tipo | Restricciones |
|-------|------|---------------|
| `id` | `BIGINT` | PK, autogenerado |
| `titulo` | `VARCHAR` | Único |
| `mensaje` | `VARCHAR` | Único |
| `fechaCreacion` | `DATETIME` | Autogenerada al crear |
| `status` | `VARCHAR` | Ej: `ABIERTO`, `CERRADO` |
| `autor` | `VARCHAR` | — |
| `curso` | `VARCHAR` | — |
| `activo` | `BOOLEAN` | `true` por defecto, `false` al eliminar |

---

## 🏗️ Estructura del Proyecto

```
forum-hub-api/
├── src/
│   ├── main/
│   │   ├── java/com/forumhub/forum_hub_api/
│   │   │   ├── controller/
│   │   │   │   ├── AutenticacionController.java
│   │   │   │   └── TopicoController.java
│   │   │   ├── domain/
│   │   │   │   ├── topico/
│   │   │   │   │   ├── Topico.java
│   │   │   │   │   ├── TopicoRepository.java
│   │   │   │   │   ├── DatosRegistroTopico.java
│   │   │   │   │   ├── DatosActualizacionTopico.java
│   │   │   │   │   ├── DatosDetalleTopico.java
│   │   │   │   │   └── DatosListaTopico.java
│   │   │   │   └── usuarios/
│   │   │   │       ├── Usuario.java
│   │   │   │       ├── UsuarioRepository.java
│   │   │   │       └── DatosAutenticacionUsuario.java
│   │   │   ├── infra/
│   │   │   │   └── security/
│   │   │   │       ├── SecurityConfigurations.java
│   │   │   │       ├── SecurityFilter.java
│   │   │   │       ├── TokenService.java
│   │   │   │       └── DatosJWTToken.java
│   │   │   └── ForumHubApiApplication.java
│   │   └── resources/
│   │       ├── db/migration/       # Scripts de migración Flyway
│   │       └── application.properties
│   └── test/
├── .env                            # Variables de entorno (no subir a Git)
├── .gitignore
├── pom.xml
└── README.md
```

---

## 👤 Autor

**Ariel Plaza**  
[GitHub](https://github.com/Ariel-Plaza)

---
