package com.example.intern.medicalrecord.mapper;

import com.example.intern.medicalrecord.dto.MedicalRecordResponse;
import com.example.intern.medicalrecord.entity.MedicalRecord;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    public MedicalRecordResponse toResponse(MedicalRecord entity) {
        return MedicalRecordResponse.builder()
                .id(entity.getId())
                .appointmentId(entity.getAppointment().getId())
                .patientName(entity.getAppointment().getPatient().getFullName())
                .doctorName(entity.getAppointment().getDoctor().getFullName())
                .symptoms(entity.getSymptoms())
                .diagnosis(entity.getDiagnosis())
                .treatmentPlan(entity.getTreatmentPlan())
                .doctorNotes(entity.getDoctorNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}