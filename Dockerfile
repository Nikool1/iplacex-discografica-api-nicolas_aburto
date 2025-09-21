# Stage 1
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar discografia.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","discografia.jar"]
