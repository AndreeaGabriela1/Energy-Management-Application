package org.application.devicesimulator.publisher;

import org.application.devicesimulator.dtos.MeasurementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer
{

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(MeasurementDto measurementDto)
    {
        try {
            LOGGER.info(String.format("Json message sent -> %s", measurementDto.toString()));
            rabbitTemplate.convertAndSend(exchange, routingJsonKey, measurementDto);
        } catch (Exception e){
            LOGGER.error("Error sending message to RabbitMQ", e);
        }
    }

}