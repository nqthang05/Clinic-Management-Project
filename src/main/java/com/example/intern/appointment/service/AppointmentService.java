package com.example.intern.appointment.service;

import com.example.intern.appointment.dto.AppointmentRequest;
import com.example.intern.appointment.dto.AppointmentResponse;
import com.example.intern.appointment.entity.Appointment;
import com.example.intern.appointment.mapper.AppointmentMapper;
import com.example.intern.appointment.repository.AppointmentRepository;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.doctor.repository.DoctorRepository;
import com.example.intern.patient.entity.Patient;
import com.example.intern.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // 1. Kiểm tra tồn tại
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Patient ID: " + request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Doctor ID: " + request.getDoctorId()));

        // 2. Logic nghiệp vụ cơ bản: Bác sĩ đã có lịch hẹn ở giờ đó chưa? (Có thể tuỳ chỉnh lại sau)
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(request.getDoctorId(), request.getAppointmentTime())) {
            throw new RuntimeException("Bác sĩ đã có lịch hẹn vào thời gian này. Vui lòng chọn giờ khác.");
        }

        Appointment appointment = appointmentMapper.toEntity(request, patient, doctor);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}