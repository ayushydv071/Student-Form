FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/student-registration-1.0.jar app.jar

COPY src/main/resources/index.html index.html

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]