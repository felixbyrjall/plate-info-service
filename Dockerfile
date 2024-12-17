FROM openjdk:21
COPY /target/plate-info-0.0.1-SNAPSHOT.jar /app/plate-info.jar
WORKDIR /app
EXPOSE 8081
CMD ["java", "-jar", "plate-info.jar"]
