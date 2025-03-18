package org.application.devicesimulator;

import org.application.devicesimulator.services.CsvReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DeviceSimulatorApplication implements CommandLineRunner
{
	@Autowired
	private CsvReader csvReader;
	public static void main(String[] args) {
		SpringApplication.run(DeviceSimulatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		///String csvFilePath = "D:\\Facultate\\AC-CTI\\CTI\\TI anul IV\\sem 1\\SD\\Laborator\\device-simulator2\\sensor.csv";//   ./sensor.csv";
		String csvFilePath = "/data/sensor.csv";
		csvReader.startCsvReading(csvFilePath);
	}
}