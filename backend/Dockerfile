FROM openjdk:19

WORKDIR /backend

COPY ./target/gps-0.0.1-SNAPSHOT.jar /backend
COPY ./src/main/resources/static/city.dat /backend/src/main/resources/static/
COPY ./src/main/resources/static/edge.dat /backend/src/main/resources/static/

EXPOSE 8080

CMD ["java", "-jar", "gps-0.0.1-SNAPSHOT.jar"]