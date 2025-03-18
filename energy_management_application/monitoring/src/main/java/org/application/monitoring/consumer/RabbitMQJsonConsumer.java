package org.application.monitoring.consumer;

import org.application.monitoring.dtos.MeasurementDto;
import org.application.monitoring.services.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer
{
    @Autowired
    MeasurementService measurementService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consumeJsonMessage(MeasurementDto measurementDto)
    {
        LOGGER.info(String.format("Received JSON message -> %s", measurementDto.toString()));
        measurementService.addMonitoring(measurementDto);
    }
}