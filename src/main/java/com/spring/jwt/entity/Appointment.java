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
public class Appointment {

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
    private Integer appointmentId;

    @Column(length = 45)
    private String appointmentDate;

    @Column(length = 45)
    private String customerName;

    @Column(length = 45)
    private String mobileNo;

    @Column(length = 45)
    private String vehicleNo;

    @Column(length = 45)
    private String vehicleMaker;

    @Column(length = 45)
    private String vehicleModel;

    @Column(length = 45)
    private String manufacturedYear;

    @Column(length = 45)
    private String kilometerDriven;

    @Column(length = 45)
    private String fuelType;

    @Column(length = 45)
    private String workType;

    @Column(length = 45)
    private String vehicleProblem;

    @Column(length = 45)
    private String pickUpAndDropService;

    @Column
    private Integer userId;
//
//    public Integer getAppointmentId() {
//        return appointmentId;
//    }
//
//
//    public void setAppointmentId(final Integer appointmentId) {
//        this.appointmentId = appointmentId;
//    }
//
//    public String getAppointmentDate() {
//        return appointmentDate;
//    }
//
//    public void setAppointmentDate(final String appointmentDate) {
//        this.appointmentDate = appointmentDate;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(final String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getMobileNo() {
//        return mobileNo;
//    }
//
//    public void setMobileNo(final String mobileNo) {
//        this.mobileNo = mobileNo;
//    }
//
//    public String getVehicleNo() {
//        return vehicleNo;
//    }
//
//    public void setVehicleNo(final String vehicleNo) {
//        this.vehicleNo = vehicleNo;
//    }
//
//    public String getVehicleMaker() {
//        return vehicleMaker;
//    }
//
//    public void setVehicleMaker(final String vehicleMaker) {
//        this.vehicleMaker = vehicleMaker;
//    }
//
//    public String getVehicleModel() {
//        return vehicleModel;
//    }
//
//    public void setVehicleModel(final String vehicleModel) {
//        this.vehicleModel = vehicleModel;
//    }
//
//    public String getManufacturedYear() {
//        return manufacturedYear;
//    }
//
//    public void setManufacturedYear(final String manufacturedYear) {
//        this.manufacturedYear = manufacturedYear;
//    }
//
//    public String getKilometerDriven() {
//        return kilometerDriven;
//    }
//
//    public void setKilometerDriven(final String kilometerDriven) {
//        this.kilometerDriven = kilometerDriven;
//    }
//
//    public String getFuelType() {
//        return fuelType;
//    }
//
//    public void setFuelType(final String fuelType) {
//        this.fuelType = fuelType;
//    }
//
//    public String getWorkType() {
//        return workType;
//    }
//
//    public void setWorkType(final String workType) {
//        this.workType = workType;
//    }
//
//    public String getVehicleProblem() {
//        return vehicleProblem;
//    }
//
//    public void setVehicleProblem(final String vehicleProblem) {
//        this.vehicleProblem = vehicleProblem;
//    }
//
//    public String getPickUpAndDropService() {
//        return pickUpAndDropService;
//    }
//
//    public void setPickUpAndDropService(final String pickUpAndDropService) {
//        this.pickUpAndDropService = pickUpAndDropService;
//    }

}
