package com.application.device.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device")
public class Device {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "maximum_hourly_energy_consumption")
    private Float maximumConsumption;
}
