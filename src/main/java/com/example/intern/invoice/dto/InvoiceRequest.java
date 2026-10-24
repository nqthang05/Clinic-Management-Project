package com.example.intern.invoice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceRequest {
    @NotNull(message = "Appointment ID không được để trống")
    private Long appointmentId;

    private String paymentMethod;
    private String notes;

    @NotEmpty(message = "Danh sách chi tiết hóa đơn không được để trống")
    private List<InvoiceItemRequest> items;

    @Data
    public static class InvoiceItemRequest {
        private Long medicineId; // Có thể null nếu là dịch vụ khám
        @NotNull(message = "Tên dịch vụ/thuốc không được để trống")
        private String itemName;

        @NotNull(message = "Loại (SERVICE/MEDICINE/OTHER) không được để trống")
        private String itemType;

        @NotNull
        private Integer quantity;

        @NotNull
        private BigDecimal unitPrice;
    }
}