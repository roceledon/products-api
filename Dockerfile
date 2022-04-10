FROM maven:3.8.3-eclipse-temurin-11 AS build

COPY ./pom.xml /usr/src/app/pom.xml
RUN mvn --file /usr/src/app/pom.xml -B install -DskipTests

COPY . /usr/src/app
RUN mvn --file /usr/src/app/pom.xml package -DskipTests


FROM eclipse-temurin:11 as debug
LABEL maintainer="Roberto Celedón<roberto.celedon.a@gmail.com>"

COPY --from=build /usr/src/app/target/product.jar /usr/src/app/product.jar

EXPOSE 8080 8000

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000", "-jar", "/usr/src/app/product.jar"]
