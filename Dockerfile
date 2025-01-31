
FROM maven:3.9-eclipse-temurin-23 AS builder
WORKDIR /app
COPY . /app
RUN chmod +x mvnw || true
RUN ./mvnw clean package -DskipTests
FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8081
RUN ls -l /app
CMD ["java", "-jar", "/app/app.jar"]
