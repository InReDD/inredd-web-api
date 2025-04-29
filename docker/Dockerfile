# ── Build stage ──
FROM maven:3.8-jdk-11 AS build
WORKDIR /app

# Cache de dependências
COPY pom.xml mvnw ./
# COPY .mvn .mvn

# Fonte e script de espera
COPY src ./src
COPY docker/wait-for-postgres.sh ./
RUN chmod +x wait-for-postgres.sh

# Gera o fat-jar
RUN mvn clean package -DskipTests

# ── Runtime stage ──
FROM eclipse-temurin:11-jdk-jammy
RUN apt-get update && apt-get install -y postgresql-client

WORKDIR /app

# Copia o JAR e o script
COPY --from=build /app/target/*.jar ./app.jar
COPY --from=build /app/wait-for-postgres.sh ./
RUN chmod +x wait-for-postgres.sh

EXPOSE 8881

ENTRYPOINT ["./wait-for-postgres.sh"]
CMD ["java", "-jar", "app.jar"]
