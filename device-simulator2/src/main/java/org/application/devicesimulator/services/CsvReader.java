package org.application.devicesimulator.services;

import org.application.devicesimulator.controllers.MessageJsonController;
import org.application.devicesimulator.dtos.MeasurementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CsvReader
{

    @Autowired
    private MessageJsonController messageJsonController;

    @Autowired
    public CsvReader(MessageJsonController messageJsonController)
    {
        this.messageJsonController = messageJsonController;
    }

    public void startCsvReading (String csvFilePath)
    {
        Thread readerThread = new Thread(() -> {
            readAndSendCsv(csvFilePath);
        });
        readerThread.start();
    }

    private static MeasurementDto convertCsvToMonitoringDTO(int userInput, String csvLine)
    {
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setDeviceId(userInput);
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        measurementDto.setTimestamp(date);
        Float measuredValue = Float.parseFloat(csvLine);
        measurementDto.setMeasurementValue(measuredValue);
        return measurementDto;
    }

    private void readAndSendCsv(String filePath)
    {
//        System.out.print("Enter device id: ");
//        Scanner scanner = new Scanner(System.in);
//        int userInput = 0;
//        if (scanner.hasNextInt())
//        {
//            userInput = scanner.nextInt();
//            System.out.println("You entered: " + userInput);
//        }
//        else
//        {
//            System.out.println("Invalid input. Please enter an integer.");
//        }
//        scanner.close();
        int userInput = 0;
        try {
            // Creează un obiect File pentru fișierul dorit
            File fisier = new File("/data/id_device.txt");  ///"/data/id_device.txt" "id_device.txt"

            // Creează un Scanner pentru a citi datele din fișier
            Scanner scanner = new Scanner(fisier);

            // Verifică dacă fișierul conține numere
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt(); // Citește primul număr întreg din fișier
                System.out.println("Numărul citit din fișier este: " + userInput);
            } else {
                System.out.println("Fișierul nu conține un număr întreg.");
            }

            // Închide Scanner-ul
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fișierul nu a fost găsit: " + e.getMessage());
        }

        while(true)
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    MeasurementDto measurementDto = convertCsvToMonitoringDTO(userInput, line);
                    //System.out.println(monitoringDTO);
                    messageJsonController.sendJsonMessage(measurementDto);
                    Thread.sleep(10000);  //10 s
                }
                System.out.println("-------- End of CSV Data --------");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


