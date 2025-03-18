FROM openjdk:17-jdk

COPY /target/monitoring.jar .

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "monitoring.jar"]