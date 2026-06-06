# Aplicación de servicio de autenticación para el acceso de una plataforma libre para el desarrollo

## 📋 Tabla de Contenido

- [Descripción del proyecto](#Descripción-del-proyecto)
- [Tecnologías utilizadas](#Tecnologías-utilizadas)
- [Arquitectura del proyecto](#Arquitectura-del-proyecto)
- [Prerrequisitos del sistema](#Prerrequisitos-del-sistema)
- [Configuración de despliegue (Docker)](#Configuración-de-despliegue-(Docker))
- [Prerrequisitos del sistema](#Prerrequisitos-del-sistema)
- [Documentación de las API](#Documentación-de-las-API)
- [Glosario](#Glosario)

## Descripción del proyecto

<p style="text-align: justify;">
    Aplicativo web monolítico centralizado en el inicio de sesión para un usuario que por medio de la verificación de las credenciales de acceso y con un rol predeterminado podrá ingresar a determinada áreas de la aplicación. La plataforma a la que ingresara esta libre para futuros desarrollos. Además, la aplicación también esta dockerizada para su despliegue con Docker.
</p>

## Tecnologías utilizadas

La aplicación fue desarrollada con tres principales tecnologías. PostgreSQL, Spring-boot, Angular.

- **PostgreSQL** se dedicó un archivo llamado ```init.sql``` para contener los Script necesarios para la creación de las tablas y datos iniciales de la base de datos del sistema.
- En **Sprint boot** se decidió configurar el proyecto con el lenguaje de programación JAVA para el desarrollo. Además, se usó Apache Maven para la compilación de proyecto, al igual se usó la versión 4.0.6 de Spring boot todo esto empaquetado en un archivo JAR. Se empleó la versión 17 de JDK de la máquina virtual de JAVA. Como dependencias de l sistema se usaron: 
    - Persistencia y Base de Datos
        - Spring-boot-starter-data-jpa: Versión heredada del Parent (3.4.3).
        - Postgresql: Versión heredada del Parent (Gestionada para compatibilidad en tiempo de ejecución).
    - Web y Seguridad
        - Spring-boot-starter-web: Versión heredada del Parent (3.4.3).
        - Spring-boot-starter-security: Versión heredada del Parent (3.4.3).
        - Java-jwt: Versión 4.4.0.
    - Vistas (Thymeleaf)
        - Spring-boot-starter-thymeleaf: Versión heredada del Parent (3.4.3).
        - Thymeleaf-extras-springsecurity6: Versión heredada del Parent (Sincronizada con Spring Security 6).
    - Herramientas de Desarrollo
        - Lombok: Versión heredada del Parent (Manejada de forma óptima por Spring Boot).
        - Mapstruct: Versión 1.6.3.
        - Springdoc-openapi-starter-webmvc-ui: Versión 2.5.0.
        - Hibernate-jpamodelgen: Versión 6.4.4.Final.
    - Capa de Pruebas (Testing)
        - Spring-boot-starter-test: Versión heredada del Parent (3.4.3).
        - Spring-security-test: Versión heredada del Parent (3.4.3).
    - Validación
        - Hibernate-validator: Versión heredada del Parent (Encargada de procesar las anotaciones de validación de datos).
 
    - Para el desarrollo del Frontend se utilizó **Angular 17** como la interfaz de usuario (UI) del proyecto. Se implementó una arquitectura modularizada apoyada en el uso de **Signals** (Señales) para la gestión eficiente del estado y la reactividad, junto con sus respectivos servicios de peticiones (HttpClient) para el consumo de la API expuesta por el Backend. 


## Arquitectura del proyecto

## Prerrequisitos del sistema

## Configuración de despliegue (Docker)

## Documentación de las API

## Glosario
- Monolítico: Una arquitectura monolítica es un modelo de desarrollo de software donde todos los componentes de una aplicación (la interfaz de usuario, la lógica de negocio y el acceso a datos) están unificados en un solo código fuente y una única unidad de ejecución.
- UI: Interfaz de Usuario.
- Arquitectura modularizada: En lugar de "modulada" (modulada se usa más en telecomunicaciones o música, en software se dice modular o modularizada).
- Consumo de la data: Es más elegante decir Consumo de la API o Consumo de los servicios.