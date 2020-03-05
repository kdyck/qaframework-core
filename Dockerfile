# Build with maven
FROM maven:3.6.3-jdk-8-slim AS build
COPY src /usr/src/app/src
COPY src/main/resources/test-configs/docker-testng.xml /usr/src/app
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml -T 1C clean package

# Build JDK
FROM openjdk:8-jdk-alpine
RUN apk update && apk add --no-cache bash curl vim

VOLUME /usr/src/app/log

WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/*-SHADED.jar /usr/src/app/target/QAREPO-CORE-1.0.0-SHADED.jar
COPY --from=build /usr/src/app/docker-testng.xml /usr/src/app/docker-testng.xml
COPY src/main/resources/test-data src/main/resources/test-data

ENV JAVA_OPTIONS "-Xms256m -Xmx512m -Ddebug -Xmx128m -Djava.awt.headless=true"
ENV JAVA_OPTIONS "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"

ENTRYPOINT ["java", "-jar", "target/QAREPO-CORE-1.0.0-SHADED.jar", "docker-testng.xml"]
