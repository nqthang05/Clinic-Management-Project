package com.example.intern.patient.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.patient.dto.PatientRequest;
import com.example.intern.patient.dto.PatientResponse;
import com.example.intern.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping
    public ApiResponse<PatientResponse> createPatient(@RequestBody PatientRequest request) {
        return ApiResponse.<PatientResponse>builder().result(patientService.createPatient(request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ApiResponse<List<PatientResponse>> getAllPatients() {
        return ApiResponse.<List<PatientResponse>>builder().result(patientService.getAllPatients()).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping("/{id}")
    public ApiResponse<PatientResponse> getPatientById(@PathVariable Long id) {
        return ApiResponse.<PatientResponse>builder().result(patientService.getPatientById(id)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PutMapping("/{id}")
    public ApiResponse<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody PatientRequest request) {
        return ApiResponse.<PatientResponse>builder().result(patientService.updatePatient(id, request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ApiResponse.<String>builder().result("Xóa bệnh nhân thành công").build();
    }
}