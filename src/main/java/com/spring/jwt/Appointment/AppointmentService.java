package com.spring.jwt.Appointment;


import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> getAllAppointments();
    AppointmentDto getAppointmentById(Integer id);
    AppointmentDto saveAppointment(AppointmentDto appointmentDto);
    void deleteAppointment(Integer id);
}

