FROM openjdk:latest
EXPOSE 8083
ADD target/stock-service.jar stock-service.jar
ENTRYPOINT ["sh", "-c", "java -jar /stock-service.jar"]