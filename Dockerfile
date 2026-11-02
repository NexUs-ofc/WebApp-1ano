# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
RUN mvn --batch-mode --no-transfer-progress dependency:go-offline
COPY src ./src
RUN mvn --batch-mode --no-transfer-progress package -DskipTests -Dassembly.skipAssembly=true

FROM tomcat:11.0-jre21-temurin AS runtime
RUN groupadd --gid 10001 app && useradd --uid 10001 --gid app --no-create-home app     && chown -R 10001:10001 /usr/local/tomcat
COPY --from=build --chown=10001:10001 /workspace/target/app.war /usr/local/tomcat/webapps/ROOT.war
USER 10001:10001
EXPOSE 8080
CMD ["catalina.sh", "run"]
