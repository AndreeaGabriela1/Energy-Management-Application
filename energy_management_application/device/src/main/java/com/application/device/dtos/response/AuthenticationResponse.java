package com.application.device.dtos.response;

import com.application.device.dtos.DeviceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse
{
    private DeviceDto user;
    private String accessToken;
}
