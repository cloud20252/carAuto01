package com.spring.jwt.Appointment;

import com.spring.jwt.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto getAppointmentById(Integer id) {
        return appointmentRepository.findById(id)
                .map(AppointmentDto::new)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with ID: " + id));
    }

    @Override
    public AppointmentDto saveAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setCustomerName(appointmentDto.getCustomerName());
        appointment.setMobileNo(appointmentDto.getMobileNo());
        appointment.setVehicleNo(appointmentDto.getVehicleNo());
        appointment.setVehicleMaker(appointmentDto.getVehicleMaker());
        appointment.setVehicleModel(appointmentDto.getVehicleModel());
        appointment.setManufacturedYear(appointmentDto.getManufacturedYear());
        appointment.setKilometerDriven(appointmentDto.getKilometerDriven());
        appointment.setFuelType(appointmentDto.getFuelType());
        appointment.setWorkType(appointmentDto.getWorkType());
        appointment.setVehicleProblem(appointmentDto.getVehicleProblem());
        appointment.setPickUpAndDropService(appointmentDto.getPickUpAndDropService());

        appointment = appointmentRepository.save(appointment);
        return new AppointmentDto(appointment);
    }

    @Override
    public void deleteAppointment(Integer id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NoSuchElementException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
