package com.example.intern.medicalrecord.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordRequest {
    @NotNull(message = "Appointment ID không được để trống")
    private Long appointmentId;

    private String symptoms;
    private String diagnosis;
    private String treatmentPlan;
    private String doctorNotes;
}