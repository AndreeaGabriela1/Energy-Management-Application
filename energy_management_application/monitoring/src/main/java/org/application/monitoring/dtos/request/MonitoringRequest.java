package org.application.monitoring.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringRequest
{
    private Integer id;
    private Integer deviceId;
    private Date timestamp;
    private Float measurementValue;
}
