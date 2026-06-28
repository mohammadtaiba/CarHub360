FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn -B clean package

FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk17
COPY --from=build /build/target/carhub360.war /opt/jboss/wildfly/standalone/deployments/carhub360.war
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
