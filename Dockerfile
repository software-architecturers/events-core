FROM openjdk:11 AS build

RUN mkdir -p /images
ENV APP /app
WORKDIR $APP

COPY settings.gradle gradlew ./
COPY gradle ./gradle
COPY core/build.gradle ./core/
COPY miss-kpi/build.gradle ./miss-kpi/
RUN ./gradlew build || return 0
COPY core ./core
COPY miss-kpi ./miss-kpi
RUN ./gradlew build


FROM openjdk:11

ENV APP /app
WORKDIR $APP
COPY --from=build "${APP}/core/build/libs/core-0.0.1-SNAPSHOT.jar" ./
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/core-0.0.1-SNAPSHOT.jar"]