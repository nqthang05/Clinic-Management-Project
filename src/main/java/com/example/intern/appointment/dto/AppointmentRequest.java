package com.example.intern.appointment.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @NotNull(message = "Patient ID không được để trống")
    private Long patientId;

    @NotNull(message = "Doctor ID không được để trống")
    private Long doctorId;

    @NotNull(message = "Thời gian hẹn không được để trống")
    @FutureOrPresent(message = "Thời gian hẹn phải ở hiện tại hoặc tương lai")
    private LocalDateTime appointmentTime; // Định dạng "yyyy-MM-ddTHH:mm:ss"

    @NotBlank(message = "Lý do khám không được để trống")
    private String reason;

    private String note;
}