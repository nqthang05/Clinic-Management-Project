package com.example.intern.appointment.mapper;

import com.example.intern.appointment.dto.AppointmentRequest;
import com.example.intern.appointment.dto.AppointmentResponse;
import com.example.intern.appointment.entity.Appointment;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest request, Patient patient, Doctor doctor) {
        return Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .note(request.getNote())
                // Status tự set default là PENDING
                .build();
    }

    public AppointmentResponse toResponse(Appointment entity) {
        return AppointmentResponse.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .patientName(entity.getPatient().getFullName()) // Lazy load sẽ query thêm
                .doctorId(entity.getDoctor().getId())
                .doctorName(entity.getDoctor().getFullName())
                .appointmentTime(entity.getAppointmentTime())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .note(entity.getNote())
                .build();
    }
}