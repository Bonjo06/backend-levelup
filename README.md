# Backend LevelUp

Este proyecto es el backend para la plataforma LevelUp, desarrollado con Spring Boot y Java. Proporciona una API REST para la gestión de usuarios, autenticación, carrito de compras, pedidos y productos.

## Características principales
- Spring Boot 3.5.7 + Java 17/24
- Seguridad con JWT y Spring Security
- Documentación automática con Swagger (Springdoc OpenAPI)
- Acceso a base de datos MySQL
- Configuración flexible mediante archivos de propiedades
- Preparado para despliegue en AWS

## Estructura del proyecto
```
Backend/
  levelUp/
    levelUp/
      src/
        main/
          java/
            levelUp/levelUp/
              Controller/   # Controladores REST
              Model/        # Entidades JPA
              Repository/   # Repositorios Spring Data
              Security/     # Configuración de seguridad
              Service/      # Lógica de negocio
        resources/
          application.properties
          application.properties.example
      pom.xml
```

## Endpoints principales
- Autenticación: `/api/auth/*`
- Usuarios: `/api/users/*`
- Carrito: `/api/cart/*`
- Pedidos: `/api/purchase-orders/*`
- Productos: `/api/inventario/*`

## Swagger
La documentación de la API está disponible en:
```
http://localhost:8080/doc/swagger-ui.html
```

## Configuración
Edita `application.properties.example` y copia como `application.properties` para tu entorno local. Configura las URLs y credenciales según tu entorno.

## Despliegue
Preparado para despliegue en AWS (ECS, Elastic Beanstalk, etc). Configura las variables de entorno y CORS según tu frontend.

## Requisitos
- Java 17 o superior
- Maven
- MySQL

