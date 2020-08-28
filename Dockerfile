FROM openjdk:8-jdk-alpine
COPY target/workoutapp-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]