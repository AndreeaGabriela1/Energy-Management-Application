package org.application.monitoring.services;

import org.application.monitoring.dtos.MeasurementDifference;
import org.application.monitoring.dtos.MeasurementDto;
import org.application.monitoring.entities.Measurement;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MeasurementService
{
    ResponseEntity<Measurement> addMonitoring(MeasurementDto measurementDto);

    ResponseEntity<?> getMonitoredValues(HttpServletRequest request, Integer deviceId) throws IllegalAccessException;

    ResponseEntity<List<MeasurementDifference>> getDifValues(Integer deviceId);

    ResponseEntity<Map<String, Double>> getSumValues(Integer deviceId);
    //ResponseEntity<Map<String, Double>> getAverageValues(Integer deviceId);
    ResponseEntity<Map<String, Double>> getSumValuesByDate(Integer deviceId, String date);
}
