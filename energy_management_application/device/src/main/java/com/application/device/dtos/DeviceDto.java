package com.application.device.dtos;

import com.application.device.entities.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto
{
    private Integer id;
    private String userEmail;
    private String description;
    private String address;
    private Float maximumConsumption;

    public DeviceDto(Device device)
    {
        this.id = device.getId();
        this.userEmail = device.getUserEmail();
        this.description = device.getDescription();
        this.address = device.getAddress();
        this.maximumConsumption = device.getMaximumConsumption();
    }
}
