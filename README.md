# Documentación del proyecto forum-hub-api

## Clonación del proyecto

Para clonar el repositorio, sigue estos pasos:

1. **Abre tu terminal.**
2. **Navega a la carpeta deseada donde quieres clonar el proyecto:**  
   ```bash
   cd /ruta/a/tu/carpeta
   ```
3. **Clona el repositorio usando Git:**  
   ```bash
   git clone https://github.com/Ariel-Plaza/forum-hub-api.git
   ```
4. **Navega a la carpeta del proyecto:**  
   ```bash
   cd forum-hub-api
   ```

## Configuración del entorno

### Requisitos previos

- **Node.js** (versión 14 o posterior)  
- **MySQL**

### Configuración de MySQL

1. **Instala MySQL en tu máquina** (Si no lo tienes ya instalado).
2. **Crea una nueva base de datos:**  
   ```sql
   CREATE DATABASE forum_hub;
   ```
3. **Crea un usuario y otórgale permisos a la base de datos:**  
   ```sql
   CREATE USER 'usuario'@'localhost' IDENTIFIED BY 'contraseña';
   GRANT ALL PRIVILEGES ON forum_hub.* TO 'usuario'@'localhost';
   FLUSH PRIVILEGES;
   ```
   Reemplaza `'usuario'` y `'contraseña'` con tus propios valores.

### Configuración del archivo .env

En la raíz del proyecto, encontrarás un archivo de ejemplo llamado `.env.example`. Copia este archivo y renómbralo a `.env`.

1. **Edita el archivo `.env` y configura las siguientes variables:**
   ```env
   DB_HOST=localhost
   DB_USER=usuario
   DB_PASS=contraseña
   DB_NAME=forum_hub
   ```
   Reemplaza `usuario`, `contraseña` y otros valores según tu configuración.

## Ejecutando la aplicación

1. **Instala las dependencias del proyecto:**  
   ```bash
   npm install
   ```
2. **Ejecuta las migraciones y semillas de la base de datos:**  
   ```bash
   npm run migrate
   npm run seed
   ```
3. **Inicia la aplicación:**  
   ```bash
   npm start
   ```
   La aplicación se ejecutará en `http://localhost:3000` por defecto.

## Ejemplos de Endpoints de la API

Aquí hay algunos ejemplos de cómo interactuar con la API:

- **Obtener todos los foros:**  
   ```bash
   GET http://localhost:3000/api/forums
   ```
- **Crear un nuevo foro:**  
   ```bash
   POST http://localhost:3000/api/forums
   Body:
   {
       "titulo": "Nuevo Foro",
       "descripcion": "Descripción del foro."
   }
   ```

## Solución de problemas

- **Error: "Base de datos no encontrada"**: Asegúrate de que la base de datos existe y de que las configuraciones en el archivo `.env` son correctas.
- **Error de conexión a MySQL**: Verifica que MySQL esté corriendo y que las credenciales sean las correctas.

## Instrucciones de prueba

1. **Asegúrate de que la aplicación se esté ejecutando.**
2. **Ejecuta las pruebas usando:**  
   ```bash
   npm test
   ```
   Esto ejecutará todas las pruebas definidas en el proyecto.