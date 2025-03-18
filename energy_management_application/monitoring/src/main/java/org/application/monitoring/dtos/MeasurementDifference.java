package org.application.monitoring.dtos;
import lombok.Data;

import java.util.Date;


@Data
public class MeasurementDifference
{
    private Date timestamp;
    private double difference;

    public MeasurementDifference(Date timestamp, double difference)
    {
        this.timestamp = timestamp;
        this.difference = difference;
    }
}


