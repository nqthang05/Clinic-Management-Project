package com.example.intern.appointment.service;

import com.example.intern.appointment.dto.AppointmentRequest;
import com.example.intern.appointment.dto.AppointmentResponse;
import com.example.intern.appointment.entity.Appointment;
import com.example.intern.appointment.mapper.AppointmentMapper;
import com.example.intern.appointment.repository.AppointmentRepository;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.doctor.repository.DoctorRepository;
import com.example.intern.exception.AppException;
import com.example.intern.exception.ErrorCode;
import com.example.intern.patient.entity.Patient;
import com.example.intern.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(request.getDoctorId(), request.getAppointmentTime())) {
            throw new AppException(ErrorCode.APPOINTMENT_CONFLICT);
        }

        Appointment appointment = appointmentMapper.toEntity(request, patient, doctor);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toResponse).collect(Collectors.toList());
    }
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        // Chỉ cho phép sửa nếu lịch chưa hoàn thành/hủy
        if ("COMPLETED".equals(appointment.getStatus()) || "CANCELLED".equals(appointment.getStatus())) {
            throw new RuntimeException("Không thể sửa lịch hẹn đã hoàn thành hoặc đã hủy");
        }

        // Kiểm tra xem đổi sang giờ mới có trùng lịch BS không
        if (!appointment.getAppointmentTime().equals(request.getAppointmentTime()) &&
                appointmentRepository.existsByDoctorIdAndAppointmentTime(request.getDoctorId(), request.getAppointmentTime())) {
            throw new AppException(ErrorCode.APPOINTMENT_CONFLICT);
        }

        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setNote(request.getNote());

        // Nếu muốn thay đổi cả bác sĩ/bệnh nhân thì code tương tự hàm Create

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    // HỦY LỊCH HẸN
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if ("COMPLETED".equals(appointment.getStatus())) {
            throw new RuntimeException("Lịch hẹn đã khám xong, không thể hủy");
        }

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
}