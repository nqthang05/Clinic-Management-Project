package com.example.intern.prescription.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.prescription.dto.PrescriptionRequest;
import com.example.intern.prescription.dto.PrescriptionResponse;
import com.example.intern.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ApiResponse<PrescriptionResponse> createPrescription(@Valid @RequestBody PrescriptionRequest request) {
        return ApiResponse.<PrescriptionResponse>builder().result(prescriptionService.createPrescription(request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {
        return ApiResponse.<List<PrescriptionResponse>>builder().result(prescriptionService.getAllPrescriptions()).build();
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/{id}/notes")
    public ApiResponse<PrescriptionResponse> updatePrescriptionNotes(@PathVariable Long id, @RequestParam String notes) {
        return ApiResponse.<PrescriptionResponse>builder().result(prescriptionService.updatePrescriptionNotes(id, notes)).build();
    }
}