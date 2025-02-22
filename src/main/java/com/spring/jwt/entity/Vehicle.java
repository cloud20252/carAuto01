package com.spring.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


@Entity
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

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(final Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(final Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(final String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(final String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAadharNo() {
        return customerAadharNo;
    }

    public void setCustomerAadharNo(final String customerAadharNo) {
        this.customerAadharNo = customerAadharNo;
    }

    public String getCustomerGstin() {
        return customerGstin;
    }

    public void setCustomerGstin(final String customerGstin) {
        this.customerGstin = customerGstin;
    }

    public String getSuperwiser() {
        return superwiser;
    }

    public void setSuperwiser(final String superwiser) {
        this.superwiser = superwiser;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(final String technician) {
        this.technician = technician;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(final String worker) {
        this.worker = worker;
    }

}
