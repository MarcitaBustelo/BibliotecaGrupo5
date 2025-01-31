# Etapa 1: Construcción del proyecto con Maven y Java 23 en una sola imagen
FROM maven:3.9-eclipse-temurin-23 AS builder

# Definir el directorio de trabajo
WORKDIR /app

# Copiar el código fuente del proyecto
COPY . /app

# Dar permisos al wrapper de Maven
RUN chmod +x mvnw || true

# Construir el proyecto y generar el JAR
RUN ./mvnw clean package -DskipTests

# Segunda etapa: Ejecutar la aplicación con Java 23
FROM maven:3.9-eclipse-temurin-23

# Verificar que Java está instalado correctamente
RUN java -version

# Definir el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de compilación
COPY --from=builder /app/target/BibliotecaGrupo5-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto 8081
EXPOSE 8081

# Verificar que el JAR se copió correctamente
RUN ls -l /app

# Ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]
