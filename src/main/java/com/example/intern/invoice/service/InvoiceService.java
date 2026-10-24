package com.example.intern.invoice.service;

import com.example.intern.appointment.entity.Appointment;
import com.example.intern.appointment.repository.AppointmentRepository;
import com.example.intern.exception.AppException;
import com.example.intern.exception.ErrorCode;
import com.example.intern.invoice.dto.InvoiceRequest;
import com.example.intern.invoice.dto.InvoiceResponse;
import com.example.intern.invoice.entity.Invoice;
import com.example.intern.invoice.entity.InvoiceItem;
import com.example.intern.invoice.mapper.InvoiceMapper;
import com.example.intern.invoice.repository.InvoiceRepository;
import com.example.intern.medicine.entity.Medicine;
import com.example.intern.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if (invoiceRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new AppException(ErrorCode.INVOICE_EXISTED);
        }

        Invoice invoice = Invoice.builder()
                .appointment(appointment)
                .paymentMethod(request.getPaymentMethod())
                .notes(request.getNotes())
                .status("UNPAID")
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (InvoiceRequest.InvoiceItemRequest itemReq : request.getItems()) {
            InvoiceItem item = InvoiceItem.builder()
                    .invoice(invoice)
                    .itemName(itemReq.getItemName())
                    .itemType(itemReq.getItemType())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(itemReq.getUnitPrice())
                    .build();

            if ("MEDICINE".equals(itemReq.getItemType()) && itemReq.getMedicineId() != null) {
                Medicine medicine = medicineRepository.findById(itemReq.getMedicineId()).orElse(null);
                item.setMedicine(medicine);
            }

            BigDecimal lineTotal = itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);
            invoice.getItems().add(item);
        }

        invoice.setTotalAmount(totalAmount);
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(invoiceMapper::toResponse).collect(Collectors.toList());
    }

    public InvoiceResponse updateInvoiceStatus(Long id, String status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        if (!status.equals("PAID") && !status.equals("CANCELLED") && !status.equals("UNPAID")) {
            throw new RuntimeException("Trạng thái hóa đơn không hợp lệ!");
        }

        // Đánh dấu hóa đơn đã thanh toán
        invoice.setStatus(status);
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }
}