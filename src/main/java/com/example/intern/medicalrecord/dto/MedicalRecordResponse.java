package com.example.intern.medicalrecord.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MedicalRecordResponse {
    private Long id;
    private Long appointmentId;
    private String patientName;
    private String doctorName;
    private String symptoms;
    private String diagnosis;
    private String treatmentPlan;
    private String doctorNotes;
    private LocalDateTime createdAt;
}