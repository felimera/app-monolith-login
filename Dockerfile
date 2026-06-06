# ==========================================
# ETAPA 1: Compilación del Frontend (Angular)
# ==========================================
FROM node:20-alpine AS frontend-builder
WORKDIR /app/frontend

# Copiar archivos de dependencias
COPY app-login-front/package*.json ./
RUN npm install

# Copiar el código fuente
COPY app-login-front/ ./

# 🚀 TRUCO: Forzamos a Docker a no usar caché en este paso para que ejecute el build real
ARG BUILD_VERSION=1
RUN npm run build -- --configuration=production

# ==========================================
# ETAPA 2: Compilación del Backend (Spring Boot)
# ==========================================
FROM maven:3.9-eclipse-temurin-17-alpine AS backend-builder
WORKDIR /app/backend

# Copiar el archivo de configuración de Maven
COPY app-login-back/pom.xml ./
# Descargar dependencias en caché para acelerar futuras compilaciones
RUN mvn dependency:go-offline

# Copiar el código fuente del backend
COPY app-login-back/src ./src

# 🚀 CORRECCIÓN DEFINITIVA DE RUTA:
# Tomamos la salida del navegador real de Angular que se inyectó en el workspace
COPY --from=frontend-builder /app/app-login-back/src/main/resources/static/browser/ ./src/main/resources/static/

# Empaquetar la aplicación omitiendo los tests para el despliegue rápido
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 3: Imagen de Ejecución Final (Ligera)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=backend-builder /app/backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
