package com.example.intern.doctor.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.doctor.dto.DoctorRequest;
import com.example.intern.doctor.dto.DoctorResponse;
import com.example.intern.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ApiResponse<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return ApiResponse.<DoctorResponse>builder().result(doctorService.createDoctor(request)).build();
    }

    @GetMapping
    public ApiResponse<List<DoctorResponse>> getAllDoctors() {
        return ApiResponse.<List<DoctorResponse>>builder().result(doctorService.getAllDoctors()).build();
    }
    @PutMapping("/{id}")
    public ApiResponse<DoctorResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequest request) {
        return ApiResponse.<DoctorResponse>builder().result(doctorService.updateDoctor(id, request)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ApiResponse.<String>builder().result("Vô hiệu hóa bác sĩ thành công").build();
    }
}