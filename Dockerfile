# Etapa 1: Construcción del proyecto usando Gradle con JDK
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

# Solo copiamos lo necesario primero para aprovechar la caché de Docker
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN ./gradlew --no-daemon build || return 0

# Luego copiamos el código fuente
COPY src src

# Copiamos application.properties.dist a application.properties
RUN cp src/main/resources/application.properties.dist \
       src/main/resources/application.properties

# Construimos el jar optimizado para producción
RUN ./gradlew --no-daemon clean bootJar

# Etapa 2: Imagen final mínima con solo el JAR ejecutable
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR desde la imagen de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Correr con menos uso de recursos en producción (opcional pero recomendado)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Exponemos el puerto que usa la aplicación Spring Boot
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
