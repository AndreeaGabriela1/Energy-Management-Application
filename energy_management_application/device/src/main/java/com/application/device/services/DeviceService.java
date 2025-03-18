package com.application.device.services;

import com.application.device.dtos.request.DeviceRequest;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService
{
    ResponseEntity<?> addDevice(HttpServletRequest accessToken, DeviceRequest deviceRequest);
    ResponseEntity<?> showAllDevices(String userEmail);
    ResponseEntity<?> showAll();
    ResponseEntity<?> modifyDevice(HttpServletRequest accessToken, DeviceRequest deviceRequest);
    ResponseEntity<?> deleteDevice(HttpServletRequest accessToken,Integer deviceId);
    ResponseEntity<?> deleteDeviceByMail(HttpServletRequest accessToken, String userEmail);

}