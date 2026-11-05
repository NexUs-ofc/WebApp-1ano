FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -B -DskipTests package

FROM tomcat:10.1-jdk21-temurin-noble

RUN rm -rf "$CATALINA_HOME/webapps/ROOT" \
    && rm -f "$CATALINA_HOME/webapps/ROOT.war"

COPY --from=build /app/target/app.war "$CATALINA_HOME/webapps/ROOT.war"

EXPOSE 10000

CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-10000}\\\"/\" \"$CATALINA_HOME/conf/server.xml\" && exec catalina.sh run"]
