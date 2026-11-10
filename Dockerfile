FROM amazoncorretto:17

WORKDIR /tmp

COPY ./target/devops.jar /tmp/devops.jar

ENTRYPOINT ["java", "-jar", "devops.jar", "db:3306", "30000"]