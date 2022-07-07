FROM openjdk:latest
EXPOSE 8082
ADD target/company-service-0.0.1-SNAPSHOT.jar company-service.jar
ENTRYPOINT ["sh", "-c", "java -jar /company-service.jar"]