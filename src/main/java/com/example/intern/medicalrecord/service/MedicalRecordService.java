package com.example.intern.medicalrecord.service;

import com.example.intern.appointment.entity.Appointment;
import com.example.intern.appointment.repository.AppointmentRepository;
import com.example.intern.medicalrecord.dto.MedicalRecordRequest;
import com.example.intern.medicalrecord.dto.MedicalRecordResponse;
import com.example.intern.medicalrecord.entity.MedicalRecord;
import com.example.intern.medicalrecord.mapper.MedicalRecordMapper;
import com.example.intern.medicalrecord.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Transactional
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Appointment ID: " + request.getAppointmentId()));

        if (medicalRecordRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new RuntimeException("Lịch hẹn này đã có hồ sơ khám bệnh!");
        }

        // Cập nhật trạng thái lịch hẹn
        appointment.setStatus("COMPLETED");
        appointmentRepository.save(appointment);

        MedicalRecord record = MedicalRecord.builder()
                .appointment(appointment)
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .treatmentPlan(request.getTreatmentPlan())
                .doctorNotes(request.getDoctorNotes())
                .build();

        return medicalRecordMapper.toResponse(medicalRecordRepository.save(record));
    }

    public List<MedicalRecordResponse> getAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toResponse)
                .collect(Collectors.toList());
    }
}