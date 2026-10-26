package com.example.intern.medicalrecord.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.medicalrecord.dto.MedicalRecordRequest;
import com.example.intern.medicalrecord.dto.MedicalRecordResponse;
import com.example.intern.medicalrecord.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ApiResponse<MedicalRecordResponse> createMedicalRecord(@Valid @RequestBody MedicalRecordRequest request) {
        return ApiResponse.<MedicalRecordResponse>builder().result(medicalRecordService.createMedicalRecord(request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ApiResponse<List<MedicalRecordResponse>> getAllMedicalRecords() {
        return ApiResponse.<List<MedicalRecordResponse>>builder().result(medicalRecordService.getAllMedicalRecords()).build();
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordRequest request) {
        return ApiResponse.<MedicalRecordResponse>builder().result(medicalRecordService.updateMedicalRecord(id, request)).build();
    }
}