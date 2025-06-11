# Build stage: compile the WAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn package

# Runtime stage: use WildFly to run the WAR
FROM jboss/wildfly:27.0.1.Final
COPY --from=build /build/target/mmra.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]