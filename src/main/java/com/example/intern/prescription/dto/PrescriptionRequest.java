package com.example.intern.prescription.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PrescriptionRequest {
    @NotNull(message = "Medical Record ID không được để trống")
    private Long medicalRecordId;

    private String notes;

    @NotEmpty(message = "Đơn thuốc phải có ít nhất 1 loại thuốc")
    private List<PrescriptionItemRequest> items;

    @Data
    public static class PrescriptionItemRequest {
        @NotNull(message = "Medicine ID không được để trống")
        private Long medicineId;
        private String dosage;
        private String frequency;
        private Integer durationDays;
        @NotNull(message = "Số lượng không được để trống")
        private Integer quantity;
        private String instructions;
    }
}