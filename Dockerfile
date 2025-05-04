# Use the official OpenJDK image as a base
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /usr/src/app

# Copy the Maven build file
COPY pom.xml ./

# Copy the source code
COPY src ./src

# Install Maven and build the application
RUN apt-get update && apt-get install -y maven && \
    mvn clean package && \
    rm -rf /var/lib/apt/lists/*

# Copy the built JAR file to a new stage to reduce image size
FROM openjdk:11-jre-slim AS runtime
WORKDIR /usr/src/app
COPY --from=0 /usr/src/app/target/your-app.jar ./your-app.jar  # Replace 'your-app.jar' with your actual jar file name

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "your-app.jar"]  # Replace 'your-app.jar' with your actual jar file name
