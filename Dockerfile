FROM openjdk:21-jdk-slim
# build arguments
ARG PROFILE_IMG_UPLOAD_DIR=/uploads/static
ARG POST_IMAGES_DIR=/uploads/static/posts

RUN mkdir -p $POST_IMAGES_DIR $POST_IMAGES_DIR
RUN mkdir -p /uploads/static /uploads/static/posts
WORKDIR /app
COPY target/short-reads-backend-0.0.1.jar short-reads-backend-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "short-reads-backend-0.0.1.jar"]
