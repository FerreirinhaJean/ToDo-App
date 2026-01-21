FROM maven:4.0.0-rc-5-eclipse-temurin-21 AS build
WORKDIR /home/app
COPY src /home/app/src/
COPY pom.xml /home/app/
RUN mvn -e -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]