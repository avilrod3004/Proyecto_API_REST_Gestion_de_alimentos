
# Proyecto de Gestión de Alimentos

Este proyecto es una API RESTful para gestionar la información sobre alimentos, incluyendo su tipo, estado, fecha de caducidad y su ubicación en el almacenamiento. La aplicación permite registrar alimentos, gestionar su existencia en diferentes ubicaciones y consultar los detalles de los mismos.

## Requisitos previos

- Tener Docker y Docker Compose instalados en tu máquina.
- Tener un entorno de desarrollo adecuado para ejecutar aplicaciones Java basadas en Spring Boot.

## Instrucciones para usar el proyecto

1. **Registrar un usuario:**

   Para utilizar la API, primero debes registrar un usuario. Puedes hacerlo enviando una solicitud `POST` a la siguiente URL:

   ```
   POST http://localhost:8080/auth/register
   ```

   Asegúrate de enviar los datos necesarios en el cuerpo de la solicitud, según el modelo de registro de usuario definido en la API.

2. **Crear un contenedor de Docker:**

   Antes de lanzar la aplicación, es necesario crear un contenedor de Docker. Asegúrate de tener el archivo `docker-compose.yml` en tu directorio de trabajo.

   Para crear y levantar los contenedores, ejecuta el siguiente comando:

   ```bash
   docker-compose up --build
   ```

Esto construirá y arrancará los contenedores necesarios para la aplicación.

3. **Lanzar la aplicación:**

   Una vez que los contenedores estén corriendo, puedes acceder a la aplicación. La API estará disponible en `http://localhost:8080` (o el puerto configurado).

4. **Consultar la documentación:**

   La documentación de la API se genera automáticamente usando OpenAPI. Puedes acceder a ella desde el siguiente enlace y usando las credenciales de un usuario:

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

   En esta página podrás ver todos los endpoints disponibles, sus descripciones y cómo utilizarlos.

## Tecnologías utilizadas

- **Spring Boot:** Framework para el desarrollo de aplicaciones backend en Java.
- **Docker:** Contenerización de la aplicación para facilitar su despliegue y ejecución en cualquier entorno.
- **OpenAPI:** Generación de documentación interactiva de la API.
