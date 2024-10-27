FROM openjdk:21-jdk-slim
RUN mkdir -p /uploads/static /uploads/static/posts
WORKDIR /app
COPY target/short-reads-backend-0.0.1.jar short-reads-backend-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "short-reads-backend-0.0.1.jar"]
