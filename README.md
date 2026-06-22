# Sistema de Gestión Fullstack - Proyecto Microservicios
Este proyecto consiste en una arquitectura de microservicios robusta y escalable diseñada para solucionar la gestión, control y sincronización de diferentes dominios de negocio de forma distribuida. El ecosistema completo está contenerizado e integrado bajo patrones de Spring Cloud.

# Índice
Contexto

Arquitectura de Microservicios

Networking (API Gateway)

Accesos y Documentación

Guía de Despliegue

Créditos

# Contexto
El dominio del problema aborda la complejidad de centralizar operaciones distribuidas (como inventario de vehículos, gestión de sucursales, reportería, pasarelas de pago, mantenimientos, seguros y notificaciones) que tradicionalmente colapsan en arquitecturas monolíticas debido al alto acoplamiento.

Solución del Proyecto: Se implementó un ecosistema basado en Microservicios con Spring Boot y Spring Cloud, donde cada dominio de negocio posee su propia base de datos independiente (mediante el patrón Database per Service) y las migraciones de esquemas están automatizadas con Flyway. Toda la configuración está centralizada y la comunicación se resuelve de forma transparente mediante un enrutador inteligente y un servidor de descubrimiento.

# Arquitectura
El sistema está compuesto por 9 microservicios activos organizados de la siguiente manera:

Servicios de Infraestructura (Spring Cloud)
eureka-service (Puerto 8761): Servidor de descubrimiento y registro de instancias.

config-server (Puerto 8888): Servidor de configuración centralizada que sirve los archivos .yml del entorno.

api-gateway (Puerto 9090): Puerta de enlace y enrutador único de peticiones hacia el ecosistema.

Servicios de Negocio (Backend)
vehiculo-service (Puerto 8083): Gestión del catálogo e inventario de vehículos (Base de datos MySQL con Flyway).

sucursal-service: Control y administración de sedes y sucursales físicas.

cliente-service: Administración y perfiles de usuarios/clientes.

mantencion-service: Historial y agendamiento de servicios mecánicos.

seguro-service: Control de pólizas y coberturas asociadas.

pago-service: Procesamiento de transacciones financieras.

reportes-service: Consolidación de datos y estadísticas del negocio.

notificacion-service: Alertas y comunicaciones automáticas del sistema.

# Networking (API Gateway)
El API Gateway está expuesto en el puerto 9090. Actúa como punto de entrada único para el frontend o clientes externos, redirigiendo el tráfico de manera interna mediante las siguientes rutas principales:

Microservicio	Ruta Base en el Gateway	Puerto Interno
Vehículos	http://localhost:9090/api/v1/vehiculos/**	8083
Sucursales	http://localhost:9090/api/v1/sucursales/**	Dinámico
Clientes	http://localhost:9090/api/v1/clientes/**	Dinámico
Mantenciones	http://localhost:9090/api/v1/mantenciones/**	Dinámico
Seguros	http://localhost:9090/api/v1/seguros/**	Dinámico
Pagos	http://localhost:9090/api/v1/pagos/**	Dinámico
Reportes	http://localhost:9090/api/v1/reportes/**	Dinámico
Notificaciones	http://localhost:9090/api/v1/notificaciones/**	Dinámico

# Accesos y Documentación
Cuando el ecosistema se encuentra encendido, se puede acceder a las consolas de administración y documentación interactiva mediante los siguientes enlaces:

Panel de Eureka Server: http://localhost:8761 (Monitoreo de salud de los 9 servicios).

Servidor de Configuración: http://localhost:8888/vehiculo-service/default (Verificación de propiedades activas).

Documentación Swagger (Vehículos): http://localhost:8083/swagger-ui/index.html (Prueba directa de endpoints de la API).

# Guía de Despliegue
Requisitos Previos
Docker y Docker Desktop instalados.

Java 25 (o la versión correspondiente a la configuración del proyecto).

Maven configurado localmente (opcional para entorno híbrido).

Opción A: Despliegue 100% Contenerizado (Docker) - Recomendado
Para levantar todo el ecosistema (bases de datos, servicios de infraestructura y microservicios de negocio) con un solo comando, sigue estos pasos:

Abre tu terminal en la raíz del proyecto (donde se encuentra el archivo docker-compose.yml).

Limpieza e inicio de volúmenes limpios:
docker compose down -v

Construcción del proyecto sin caché (Recompilación de archivos Java internos):
docker compose build --no-cache

Levantar todos los contenedores en segundo plano:
docker compose up -d

Verificación de logs (opcional, ejemplo para vehículos):
docker compose logs vehiculo-service
Opción B: Despliegue Híbrido / Local (Desarrollo Activo)
Si deseas realizar modificaciones en tiempo real en un microservicio específico (por ejemplo, vehiculo-service) ejecutándolo desde tu IDE (IntelliJ IDEA) mientras las bases de datos y la infraestructura corren en Docker, sigue esta secuencia:

Levantar únicamente la infraestructura y BD en Docker:
docker compose up -d mysql_database eureka config-server api-gateway

Compilar el microservicio localmente mediante Maven:

Abre la terminal del microservicio y ejecuta:
./mvnw clean package -DskipTests
Configuración de Variables de Entorno en el IDE:
Asegúrate de que tu perfil local apunte a las direcciones de los contenedores si ejecutas de forma externa, o mantén las propiedades correspondientes para que se comunique con el puerto 8888 del Config Server local.

Ejecutar la clase Application:
Corre el archivo principal (VehiculoServiceApplication.java) desde el botón de Run de IntelliJ.

# Créditos
Este proyecto fue desarrollado por el siguiente equipo:

Maximiliano Araos

