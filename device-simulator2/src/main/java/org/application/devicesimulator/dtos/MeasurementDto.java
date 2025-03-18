package org.application.devicesimulator.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class MeasurementDto
{
    private Integer deviceId;
    private Date timestamp;
    private Float measurementValue;
}
