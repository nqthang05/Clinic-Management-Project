package com.example.intern.invoice.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.invoice.dto.InvoiceRequest;
import com.example.intern.invoice.dto.InvoiceResponse;
import com.example.intern.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    public ApiResponse<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        return ApiResponse.<InvoiceResponse>builder().result(invoiceService.createInvoice(request)).build();
    }

    @GetMapping
    public ApiResponse<List<InvoiceResponse>> getAllInvoices() {
        return ApiResponse.<List<InvoiceResponse>>builder().result(invoiceService.getAllInvoices()).build();
    }
    @PatchMapping("/{id}/status")
    public ApiResponse<InvoiceResponse> updateInvoiceStatus(@PathVariable Long id, @RequestParam String status) {
        return ApiResponse.<InvoiceResponse>builder().result(invoiceService.updateInvoiceStatus(id, status)).build();
    }
}