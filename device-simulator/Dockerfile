FROM openjdk:17-jdk

COPY /target/device-simulator-0.0.1-SNAPSHOT.jar .

COPY sensor.csv /data/sensor.csv

COPY id_device.txt /data/id_device.txt

EXPOSE 8098

ENTRYPOINT ["java", "-jar", "device-simulator-0.0.1-SNAPSHOT.jar"]