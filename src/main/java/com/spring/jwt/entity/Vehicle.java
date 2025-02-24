package com.spring.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;


@Entity
@Data
public class Vehicle {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer vehicleId;

    @Column
    private Integer appointmentId;

    @Column(length = 45)
    private String chasisNumber;

    @Column(length = 45)
    private String customerAddress;

    @Column(length = 45)
    private String customerAadharNo;

    @Column(length = 45)
    private String customerGstin;

    @Column(length = 45)
    private String superwiser;

    @Column(length = 45)
    private String technician;

    @Column(length = 45)
    private String worker;


}
