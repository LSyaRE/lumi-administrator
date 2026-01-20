# Etapa 1: Construcción del proyecto usando Maven con JDK
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamos application.properties.dist a application.properties
RUN cp src/main/resources/application.properties.dist \
       src/main/resources/application.properties

# Copiamos primero los archivos necesarios para aprovechar la caché
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Descargamos dependencias (mejora el cache)
RUN ./mvnw -B -q -DskipTests dependency:go-offline || true

# Luego copiamos el código fuente
COPY src src


# Construimos el JAR optimizado para producción
RUN ./mvnw -B -DskipTests clean package

# Etapa 2: Imagen final mínima con solo el JAR ejecutable
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR desde la imagen de construcción
COPY --from=build /app/target/*.jar app.jar

# Correr con menos uso de recursos en producción
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Exponemos el puerto que usa la aplicación Spring Boot
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
