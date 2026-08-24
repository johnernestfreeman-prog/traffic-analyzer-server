# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 50051
EXPOSE 8080

ENV DB_URL=jdbc:postgresql://host.docker.internal:5432/traffic_analyzer
ENV DB_USER=traffic_app

ENTRYPOINT ["java", "-jar", "app.jar"]
