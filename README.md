# 🚀 First Challenge - Microservicio de Gestión de Usuarios

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-3.5.4-blue.svg)](https://docs.spring.io/spring-framework/reference/web/webflux.html)
[![R2DBC](https://img.shields.io/badge/R2DBC-PostgreSQL-lightblue.svg)](https://r2dbc.io/)
[![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-red.svg)](https://alistair.cockburn.us/hexagonal-architecture/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Descripción del Proyecto

**First Challenge** es un microservicio de autenticación y gestión de usuarios desarrollado con **Spring Boot 3.5.4** y **Spring WebFlux**, implementando la **Arquitectura Hexagonal (Clean Architecture)**. El sistema permite el registro y gestión de usuarios con validaciones robustas y persistencia transaccional en PostgreSQL.

## 🎯 Funcionalidades Principales

### ✅ Registro de Usuarios
- **Endpoint**: `POST /api/v1/usuarios`
- **Funcionalidad**: Registro completo de usuarios con validaciones
- **Datos requeridos**: nombres, apellidos, correo electrónico, salario base
- **Datos opcionales**: fecha de nacimiento, dirección, teléfono, documento de identidad

### ✅ Validaciones Implementadas
- **Campos obligatorios**: nombres, apellidos, correo electrónico, salario base
- **Formato de email**: Validación de formato estándar de correo electrónico
- **Rango de salario**: Entre 0 y 15,000,000
- **Unicidad de email**: Prevención de duplicados
- **Validaciones de negocio**: Reglas de dominio implementadas

### ✅ Consulta de Usuarios
- **Endpoint**: `GET /api/v1/usuarios?identityDocument={documento}`
- **Funcionalidad**: Verificación de existencia por documento de identidad

## 🏗️ Arquitectura del Sistema

### 📐 Arquitectura Hexagonal (Clean Architecture)

```
┌─────────────────────────────────────────────────────────────┐
│                    Entry Points (API)                       │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │   Reactive Web  │  │   Swagger UI    │                  │
│  │   (WebFlux)     │  │   (OpenAPI 3)   │                  │
│  └─────────────────┘  └─────────────────┘                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Use Cases (Domain)                       │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │   UserUseCase   │  │   Validations   │                  │
│  │   (Business)    │  │   (Rules)       │                  │
│  └─────────────────┘  └─────────────────┘                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Domain Model                            │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │      User       │  │   Exceptions    │                  │
│  │   (Entity)      │  │   (Business)    │                  │
│  └─────────────────┘  └─────────────────┘                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                Driven Adapters (Infrastructure)             │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │   R2DBC        │  │   PostgreSQL    │                  │
│  │   Repository   │  │   Database      │                  │
│  └─────────────────┘  └─────────────────┘                  │
└─────────────────────────────────────────────────────────────┘
```

### 🗂️ Estructura del Proyecto

```
first-challenge/
├── applications/                    # Capa de aplicación
│   └── app-service/                # Servicio principal
│       ├── MainApplication.java     # Punto de entrada
│       └── build.gradle            # Dependencias del servicio
├── domain/                         # Capa de dominio
│   ├── model/                      # Entidades y reglas de negocio
│   │   └── user/                   # Modelo de usuario
│   │       ├── User.java           # Entidad principal
│   │       ├── exceptions/         # Excepciones de dominio
│   │       └── gateways/           # Interfaces de repositorio
│   └── usecase/                    # Casos de uso
│       └── user/                   # Lógica de negocio
│           ├── IUserUseCase.java   # Interfaz del caso de uso
│           └── UserUseCase.java    # Implementación
├── infrastructure/                 # Capa de infraestructura
│   ├── driven-adapters/            # Adaptadores conducidos
│   │   └── r2dbc-postgresql/       # Persistencia PostgreSQL
│   │       ├── UserEntity.java     # Entidad de base de datos
│   │       ├── UserReactiveRepository.java
│   │       └── UserReactiveRepositoryAdapter.java
│   └── entry-points/               # Puntos de entrada
│       └── reactive-web/           # API REST reactiva
│           ├── Handler.java        # Manejadores de endpoints
│           ├── RouterRest.java     # Configuración de rutas
│           ├── dto/                # Objetos de transferencia
│           └── mapper/             # Mapeadores DTO-Entity
├── deployment/                     # Configuración de despliegue
│   └── Dockerfile                  # Contenedor Docker
├── local_enviroment/               # Entorno local
│   └── script_create_table.sql     # Script de base de datos
└── build.gradle                    # Configuración principal de Gradle
```

## 🛠️ Tecnologías y Dependencias

### 🔧 Core Technologies
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.4** - Framework de aplicación
- **Spring WebFlux** - Framework web reactivo
- **Spring Data R2DBC** - Persistencia reactiva
- **PostgreSQL** - Base de datos principal

### 📚 Dependencias Principales
```gradle
// Spring Boot Starter
implementation 'org.springframework.boot:spring-boot-starter'

// WebFlux
implementation 'org.springframework.boot:spring-boot-starter-webflux'

// R2DBC PostgreSQL
implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'
implementation 'org.postgresql:r2dbc-postgresql'

// Swagger/OpenAPI
implementation 'org.springdoc:springdoc-openapi-starter-webflux-ui'

// Lombok
compileOnly 'org.projectlombok:lombok:1.18.38'

// Testing
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'io.projectreactor:reactor-test'
```

### 🧪 Herramientas de Testing y Calidad
- **JUnit 5** - Framework de testing
- **ArchUnit** - Testing de arquitectura
- **Jacoco** - Cobertura de código
- **Pitest** - Testing de mutaciones
- **SonarQube** - Análisis de calidad

## 🚀 Instalación y Configuración

### 📋 Prerrequisitos
- Java 21 o superior
- Gradle 8.14.3 o superior
- PostgreSQL 12 o superior
- Docker (opcional)

### 🔧 Configuración de Base de Datos

1. **Crear base de datos PostgreSQL**:
```sql
CREATE DATABASE auth;
```

2. **Ejecutar script de creación de tablas**:
```bash
psql -U postgres -d auth -f local_enviroment/script_create_table.sql
```

### ⚙️ Configuración de la Aplicación

El archivo `application.yaml` contiene la configuración principal:

```yaml
server:
  port: 8080

spring:
  application:
    name: "FirstChallenge"

adapters:
  r2dbc:
    host: "localhost"
    port: 5432
    database: "auth"
    schema: "public"
    username: "postgres"
    password: "password"
```

### 🏃‍♂️ Ejecución Local

1. **Clonar el repositorio**:
```bash
git clone <repository-url>
cd first-challenge
```

2. **Compilar el proyecto**:
```bash
./gradlew build
```

3. **Ejecutar la aplicación**:
```bash
./gradlew bootRun
```

4. **Verificar que esté funcionando**:
```bash
curl http://localhost:8080/actuator/health
```

### 🐳 Ejecución con Docker

1. **Construir la imagen**:
```bash
./gradlew bootJar
docker build -t first-challenge .
```

2. **Ejecutar el contenedor**:
```bash
docker run -p 8080:8080 first-challenge
```

## 📖 API Documentation

### 🔗 Endpoints Disponibles

#### POST /api/v1/usuarios
**Registra un nuevo usuario en el sistema**

**Request Body**:
```json
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "identityDocument": "12345678",
  "birthDate": "1990-05-15",
  "address": "Av. Siempre Viva 742, Lima",
  "phoneNumber": "+51 987654321",
  "email": "juan.perez@example.com",
  "baseSalary": 3500.75
}
```

**Response (201 Created)**:
```json
{
  "id": "uuid-generated",
  "firstName": "Juan",
  "lastName": "Pérez",
  "identityDocument": "12345678",
  "birthDate": "1990-05-15",
  "address": "Av. Siempre Viva 742, Lima",
  "phoneNumber": "+51 987654321",
  "email": "juan.perez@example.com",
  "baseSalary": 3500.75
}
```

#### GET /api/v1/usuarios?identityDocument={documento}
**Verifica la existencia de un usuario por documento de identidad**

**Response (200 OK)**:
```json
{
  "exists": true
}
```

**Response (204 No Content)**:
```
Usuario no encontrado
```

### 📚 Swagger UI
La documentación interactiva de la API está disponible en:
```
http://localhost:8080/swagger-ui.html
```

## 🧪 Testing

### 🏃‍♂️ Ejecutar Tests
```bash
# Todos los tests
./gradlew test

# Tests con cobertura
./gradlew jacocoTestReport

# Tests de mutación
./gradlew pitest

# Reporte consolidado
./gradlew jacocoMergedReport
```

### 📊 Cobertura de Código
Los reportes de cobertura se generan en:
- **Jacoco**: `build/reports/jacocoHtml/`
- **Pitest**: `build/reports/pitest/`

## 📝 Logging y Monitoreo

### 📋 Configuración de Logs
- **Nivel**: INFO para Spring, DEBUG para el paquete principal
- **Archivo**: `logs/app.log`
- **Rotación**: Automática por fecha

### 🔍 Endpoints de Monitoreo
- **Health Check**: `/actuator/health`
- **Prometheus**: `/actuator/prometheus`
- **Metrics**: `/actuator/metrics`

## 🚀 Despliegue

### 🐳 Docker
El proyecto incluye un `Dockerfile` optimizado para contenedores:

```dockerfile
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY *.jar FirstChallenge.jar
ENV JAVA_OPTS="-Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70"
USER appuser
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar FirstChallenge.jar"]
```

### ☁️ Despliegue en Nube
El proyecto está preparado para despliegue en:
- **AWS ECS/Fargate**
- **Google Cloud Run**
- **Azure Container Instances**
- **Kubernetes**

## 🔒 Seguridad

### 🛡️ Características de Seguridad
- **CORS configurado** para orígenes específicos
- **Headers de seguridad** implementados
- **Validación de entrada** robusta
- **Manejo de excepciones** centralizado
- **Logs de auditoría** para operaciones críticas

### 🚫 Prevención de Vulnerabilidades
- **Validación de datos** en múltiples capas
- **Sanitización de entrada** automática
- **Manejo seguro de errores** sin exposición de información sensible

## 📈 Métricas y Observabilidad

### 📊 Métricas Disponibles
- **Tiempo de respuesta** de endpoints
- **Tasa de éxito/error** de operaciones
- **Uso de recursos** del sistema
- **Estado de la base de datos**

### 🔍 Trazabilidad
- **Logs estructurados** con contexto
- **Correlación de requests** mediante IDs únicos
- **Métricas de negocio** (usuarios registrados, validaciones fallidas)


### 🧪 Estándares de Código
- **Java Code Style**: Google Java Style Guide
- **Testing**: Mínimo 90% de cobertura
- **Documentación**: JavaDoc para métodos públicos
- **Logging**: Uso consistente de SLF4J


## 👥 Autores

- **Desarrollador**: [Fernando Rojo]
- **Proyecto**: First Challenge - Microservicio de Gestión de Usuarios
- **Fecha**: 2025

**⭐ Si este proyecto te ha sido útil, por favor dale una estrella en GitHub!**
