package org.application.monitoring.controllers;

import org.application.monitoring.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/monitoring")
@CrossOrigin(origins = "*")
public class MonitoringController
{
    @Autowired
    MeasurementService measurementService;

    @GetMapping("/getmonitoring")
    public ResponseEntity<?> getMonitoringValues(HttpServletRequest accessToken, @RequestParam("device_id") Integer deviceId) throws IllegalAccessException {
        return measurementService.getMonitoredValues(accessToken, deviceId);
    }

    @GetMapping("/getdif")
    public ResponseEntity<?> getMonitoringDifValues(@RequestParam("device_id") Integer deviceId) {
        return measurementService.getDifValues(deviceId);
    }

    @GetMapping("/getsum")
    public ResponseEntity<?> getMonitoringSumValues(@RequestParam("device_id") Integer deviceId) {
        return measurementService.getSumValues(deviceId);
    }
    @GetMapping("/getsumbydate")
    public ResponseEntity<?> getMonitoringSumValuesByDate(@RequestParam("device_id") Integer deviceId, @RequestParam("date") String date) {
        return measurementService.getSumValuesByDate(deviceId, date);
    }
}
