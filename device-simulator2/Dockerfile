FROM openjdk:17-jdk

COPY /target/device-simulator2-0.0.1-SNAPSHOT.jar .

COPY sensor.csv /data/sensor.csv

COPY id_device.txt /data/id_device.txt

EXPOSE 8097

ENTRYPOINT ["java", "-jar", "device-simulator2-0.0.1-SNAPSHOT.jar"]