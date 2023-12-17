FROM openjdk:18

COPY *.jar /app.jar

CMD ["-server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","jar","Collection-0.0.1-SNAPSHOT.jar"]


