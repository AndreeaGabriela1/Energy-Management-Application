package com.application.device.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest
{
    private Integer id;
    private String userEmail;
    private String description;
    private String address;
    private Float maximumConsumption;
}
