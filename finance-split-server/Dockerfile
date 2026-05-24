FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /finance-split
COPY pom.xml .
COPY finance-split-api/pom.xml api/
COPY finance-split-domain/pom.xml domain/
COPY finance-split-domainservice/pom.xml domainservice/
COPY finance-split-rest/pom.xml rest/
COPY finance-split-server/pom.xml server/
COPY finance-split-client/pom.xml client/
RUN mvn dependency:go-offline -pl server -am -q
COPY . .
RUN mvn clean package -pl server -am -DskipTests -q

FROM eclipse-temurin:21-jre
WORKDIR /finance-split
COPY --from=build /finance-split/finance-split-server/target/server-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]