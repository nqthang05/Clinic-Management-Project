package com.example.intern.appointment.controller;

import com.example.intern.appointment.dto.AppointmentRequest;
import com.example.intern.appointment.dto.AppointmentResponse;
import com.example.intern.appointment.service.AppointmentService;
import com.example.intern.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping
    public ApiResponse<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder().result(appointmentService.createAppointment(request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        return ApiResponse.<List<AppointmentResponse>>builder().result(appointmentService.getAllAppointments()).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PutMapping("/{id}")
    public ApiResponse<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder().result(appointmentService.updateAppointment(id, request)).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PutMapping("/{id}/cancel")
    public ApiResponse<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ApiResponse.<String>builder().result("Đã hủy lịch hẹn thành công").build();
    }
}