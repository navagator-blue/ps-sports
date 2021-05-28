FROM openjdk:11.0-jdk-slim as builder
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:11.0-jdk-slim
COPY --from=builder ./build/libs/*.jar app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
