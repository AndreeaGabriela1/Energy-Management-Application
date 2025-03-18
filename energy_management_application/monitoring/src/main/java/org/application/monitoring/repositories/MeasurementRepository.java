package org.application.monitoring.repositories;

import org.application.monitoring.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer>
{
    Optional<Measurement> findById(@Param("monitoring_id") Integer monitoringId);
    List<Measurement> findAllByDeviceIdOrderByTimestampDesc(Integer deviceId);
}
