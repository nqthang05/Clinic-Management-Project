package com.example.intern.doctor.controller;

import com.example.intern.doctor.dto.DoctorRequest;
import com.example.intern.doctor.dto.DoctorResponse;
import com.example.intern.doctor.service.DoctorService;
import jakarta.validation.Valid; // Import cực kỳ quan trọng
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // Đừng quên @Valid để kích hoạt validation
    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}