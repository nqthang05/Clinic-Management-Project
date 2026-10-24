package com.example.intern.invoice.mapper;

import com.example.intern.invoice.dto.InvoiceResponse;
import com.example.intern.invoice.entity.Invoice;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {
    public InvoiceResponse toResponse(Invoice entity) {
        return InvoiceResponse.builder()
                .id(entity.getId())
                .appointmentId(entity.getAppointment().getId())
                .patientName(entity.getAppointment().getPatient().getFullName())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .paymentMethod(entity.getPaymentMethod())
                .invoiceDate(entity.getCreatedAt())
                .items(entity.getItems().stream().map(item ->
                        InvoiceResponse.InvoiceItemResponse.builder()
                                .id(item.getId())
                                .itemName(item.getItemName())
                                .itemType(item.getItemType())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                // Tính lineTotal ở tầng DTO vì DB tự sinh không trả về ngay khi vừa save
                                .lineTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}