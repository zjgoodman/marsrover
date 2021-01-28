FROM openjdk:11.0-jre-slim

COPY build/libs/marsrover-*.jar /app/marsrover.jar

EXPOSE 8080

CMD [ "java", "-jar", "/app/marsrover.jar" ]