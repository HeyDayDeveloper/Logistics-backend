FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/Logistics-backend.jar /Logistics-backend.jar
EXPOSE 2000-3000
ENTRYPOINT ["java", "-jar", "/Logistics-backend.jar"]
