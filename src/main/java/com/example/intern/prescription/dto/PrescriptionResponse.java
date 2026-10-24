package com.example.intern.prescription.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PrescriptionResponse {
    private Long id;
    private Long medicalRecordId;
    private String notes;
    private LocalDateTime prescribedDate;
    private List<PrescriptionItemResponse> items;

    @Data
    @Builder
    public static class PrescriptionItemResponse {
        private Long id;
        private Long medicineId;
        private String medicineName;
        private String dosage;
        private String frequency;
        private Integer quantity;
        private String instructions;
    }
}