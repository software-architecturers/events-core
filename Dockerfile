FROM openjdk:11 AS build

ENV APP /app
WORKDIR $APP

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew build


FROM openjdk:11

ENV APP /app
WORKDIR $APP
COPY --from=build "${APP}/build/libs/events-core-0.0.1-SNAPSHOT.jar" ./
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/events-core-0.0.1-SNAPSHOT.jar"]