package org.application.devicesimulator.controllers;

import org.application.devicesimulator.dtos.MeasurementDto;
import org.application.devicesimulator.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoring")
public class MessageJsonController
{
    private RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer)
    {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody MeasurementDto measurementDto){
        jsonProducer.sendJsonMessage(measurementDto);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }

}
