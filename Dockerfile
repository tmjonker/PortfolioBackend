FROM openjdk:latest

WORKDIR /app
COPY target/PortfolioBackend-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "PortfolioBackend-0.0.1-SNAPSHOT.jar"]