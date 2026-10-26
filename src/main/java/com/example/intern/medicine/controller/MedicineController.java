package com.example.intern.medicine.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.medicine.dto.MedicineRequest;
import com.example.intern.medicine.dto.MedicineResponse;
import com.example.intern.medicine.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<MedicineResponse> createMedicine(@RequestBody MedicineRequest request) {
        return ApiResponse.<MedicineResponse>builder().result(medicineService.createMedicine(request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ApiResponse<List<MedicineResponse>> getAllMedicines() {
        return ApiResponse.<List<MedicineResponse>>builder().result(medicineService.getAllMedicines()).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping("/{id}")
    public ApiResponse<MedicineResponse> getMedicineById(@PathVariable Long id) {
        return ApiResponse.<MedicineResponse>builder().result(medicineService.getMedicineById(id)).build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<MedicineResponse> updateMedicine(@PathVariable Long id, @RequestBody MedicineRequest request) {
        return ApiResponse.<MedicineResponse>builder().result(medicineService.updateMedicine(id, request)).build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ApiResponse.<String>builder().result("Vô hiệu hóa thuốc thành công").build();
    }
}