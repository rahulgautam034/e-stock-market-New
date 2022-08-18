FROM openjdk:latest
EXPOSE 8081
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["sh", "-c", "java -jar /api-gateway.jar"]