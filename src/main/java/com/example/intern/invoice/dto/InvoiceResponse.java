package com.example.intern.invoice.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {
    private Long id;
    private Long appointmentId;
    private String patientName;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMethod;
    private LocalDateTime invoiceDate;
    private List<InvoiceItemResponse> items;

    @Data
    @Builder
    public static class InvoiceItemResponse {
        private Long id;
        private String itemName;
        private String itemType;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;
    }
}