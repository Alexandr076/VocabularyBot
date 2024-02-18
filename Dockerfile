FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
EXPOSE 8080
RUN mvn install --no-transfer-progress -DskipTests=true
ENTRYPOINT ["mvn", "spring-boot:run"]