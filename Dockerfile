FROM eclipse-temurin:24.0.1_9-jre-noble
LABEL authors="Hossein"

WORKDIR /app
COPY ./build/libs/springboot-with-redis-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]