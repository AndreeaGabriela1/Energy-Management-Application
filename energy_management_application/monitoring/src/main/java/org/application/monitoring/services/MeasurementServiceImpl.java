package org.application.monitoring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.application.monitoring.dtos.MeasurementDifference;
import org.application.monitoring.dtos.MeasurementDto;
import org.application.monitoring.entities.Measurement;
import org.application.monitoring.repositories.MeasurementRepository;
import org.application.monitoring.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService
{
    private final MeasurementRepository measurementRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public ResponseEntity<Measurement> addMonitoring(MeasurementDto measurementDto)
    {
        Measurement monitoring = new Measurement();
        monitoring.setDeviceId(measurementDto.getDeviceId());
        monitoring.setTimestamp(measurementDto.getTimestamp());
        monitoring.setMeasurementValue(measurementDto.getMeasurementValue());

        monitoring = measurementRepository.save(monitoring);

        return ResponseEntity.ok(monitoring);
    }
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
    @Override
    public ResponseEntity<?> getMonitoredValues(HttpServletRequest request, Integer deviceId) throws IllegalAccessException
    {
        String token = extractTokenFromRequest(request);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        String userRole = jwtUtils.getUserRoleFromJwtToken(token);
        if (!userRole.contains("CLIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        List<Measurement> monitoringList = measurementRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId);
        return ResponseEntity.ok(monitoringList);
    }

    @Override
    public ResponseEntity<List<MeasurementDifference>> getDifValues(Integer deviceId)
    {
        List<MeasurementDifference> diff = getSensorDifference(deviceId);
        return ResponseEntity.ok(diff);
    }
    private List<MeasurementDifference> getSensorDifference(Integer deviceId) {
        List<Measurement> monitoringList = measurementRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId);

        // Calculate the difference between consecutive values and order by timestamp
        List<MeasurementDifference> differences = IntStream.range(0, monitoringList.size() - 1)
                .mapToObj(i -> {
                    Measurement current = monitoringList.get(i);
                    Measurement prev = monitoringList.get(i + 1);
                    double difference = current.getMeasurementValue() - prev.getMeasurementValue();
                    if (difference < 0) {
                        difference = 0;
                    }
                    String formattedDifference = String.format("%.3f", difference);

                    return new MeasurementDifference(current.getTimestamp(), Double.parseDouble(formattedDifference));
                })
                .sorted(Comparator.comparing(MeasurementDifference::getTimestamp))
                .collect(Collectors.toList());
        return differences;
    }

    @Override
    public ResponseEntity<Map<String, Double>> getSumValues(Integer deviceId)
    {
        List<MeasurementDifference> diff = getSensorDifference(deviceId);

        Map<String, Double> sumByMinute = diff.stream()
                .collect(Collectors.groupingBy(
                        monitoringDifference -> formatMinute(monitoringDifference.getTimestamp()),
                        Collectors.summingDouble(MeasurementDifference::getDifference)
                ));

        List<Map.Entry<String, Double>> sortedEntries = sumByMinute.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        int size = sortedEntries.size();
        int startIndex = Math.max(0, size - 24); // Starting index for the last 24 entries

        List<Map.Entry<String, Double>> last24Entries = sortedEntries.subList(startIndex, size);

        Map<String, Double> result = last24Entries.stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                ));

        return ResponseEntity.ok(result);
    }
    private String formatMinute(Date timestamp) {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return minuteFormat.format(timestamp);
    }

//    @Override
//    public ResponseEntity<Map<String, Double>> getSumValuesByDate(Integer deviceId, String date)
//    {
//        List<MeasurementDifference> diff = getSensorDifference(deviceId);
//
//        Map<String, Double> sumByMinute = diff.stream()
//                .collect(Collectors.groupingBy(
//                        monitoringDifference -> formatMinute(monitoringDifference.getTimestamp()),
//                        Collectors.summingDouble(MeasurementDifference::getDifference)
//                ));
//
//        // Filter by the requested date (if it's provided)
//        if (date != null && !date.isEmpty()) {
//            sumByMinute = sumByMinute.entrySet().stream()
//                    .filter(entry -> entry.getKey().startsWith(date))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        }
//
//        List<Map.Entry<String, Double>> sortedEntries = sumByMinute.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toList());
//
//        int size = sortedEntries.size();
//        int startIndex = Math.max(0, size - 24); // Starting index for the last 24 entries
//
//        List<Map.Entry<String, Double>> last24Entries = sortedEntries.subList(startIndex, size);
//
//        Map<String, Double> result = last24Entries.stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1, LinkedHashMap::new
//                ));
//
//        return ResponseEntity.ok(result);
//    }
    @Override
    public ResponseEntity<Map<String, Double>> getSumValuesByDate(Integer deviceId, String date) {
        // Obține diferențele de măsurători pentru deviceId
        List<MeasurementDifference> diff = getSensorDifference(deviceId);

        // Grupează valorile pe minut și calculează suma
        Map<String, Double> sumByMinute = diff.stream()
                .collect(Collectors.groupingBy(
                        monitoringDifference -> formatMinute(monitoringDifference.getTimestamp()),
                        Collectors.summingDouble(MeasurementDifference::getDifference)
                ));

        // Filtrează doar intrările pentru data cerută (dacă este specificată)
        if (date != null && !date.isEmpty()) {
            sumByMinute = sumByMinute.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(date))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        // Returnează toate intrările filtrate
        return ResponseEntity.ok(sumByMinute);
    }
}
