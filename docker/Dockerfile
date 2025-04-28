# Build stage
FROM eclipse-temurin:11-jdk-jammy AS build

WORKDIR /build

RUN apt-get update && apt-get install -y maven postgresql-client

COPY . .

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:11-jre-jammy AS runtime

WORKDIR /app

RUN apt-get update && apt-get install -y postgresql-client

COPY docker/wait-for-postgres.sh /wait-for-postgres.sh
RUN chmod +x /wait-for-postgres.sh

EXPOSE 8881

ENTRYPOINT ["/wait-for-postgres.sh", "java", "-jar", "target/Inredd-0.0.1-SNAPSHOT.jar"]