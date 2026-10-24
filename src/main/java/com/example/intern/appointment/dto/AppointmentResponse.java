package com.example.intern.appointment.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientName; // Trả thêm tên để tiện hiển thị trên giao diện
    private Long doctorId;
    private String doctorName;  // Trả thêm tên BS
    private LocalDateTime appointmentTime;
    private String reason;
    private String status;
    private String note;
}