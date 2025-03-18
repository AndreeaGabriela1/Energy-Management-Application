package com.application.device.controllers;

import com.application.device.dtos.request.DeviceRequest;
import com.application.device.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/device")
@CrossOrigin(origins = "*")
public class DeviceController {

    @Autowired
    DeviceService iDeviceService;

    @PostMapping("/adddevice")
    public ResponseEntity<?> addDevice(HttpServletRequest accessToken, @RequestBody DeviceRequest device) {
        System.out.println("Add new device");
        return iDeviceService.addDevice(accessToken, device);
    }

    @GetMapping("/showalldevices")
    public ResponseEntity<?> showAllDevices(String userEmail) {
        return iDeviceService.showAllDevices(userEmail);
    }
    @GetMapping("/showall")
    public ResponseEntity<?> showAll()
    {
        return iDeviceService.showAll();
    }

    @PutMapping("/modifydevice")
    public ResponseEntity<?> modifyDevice(HttpServletRequest accessToken, @RequestBody DeviceRequest device) {
        return iDeviceService.modifyDevice(accessToken, device);
    }

    @DeleteMapping("/deletedevice")
    public ResponseEntity<?> deleteDevice(HttpServletRequest accessToken, @RequestParam Integer deviceId) {
        System.out.println("Delete Device");
        return iDeviceService.deleteDevice(accessToken, deviceId);
    }

    @DeleteMapping("/deletedevicebymail")
    public ResponseEntity<?> deleteDeviceByMail(HttpServletRequest accessToken, @RequestParam(name = "userEmail") String userEmail) {
        return iDeviceService.deleteDeviceByMail(accessToken, userEmail);
    }
}
