package com.example.intern.doctor.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponse {
    private Long id;
    private Long userId; // Chỉ trả về ID thay vì cả Object User cho gọn
    private String fullName;
    private String specialty;
    private String phone;
    private String email;
    private String licenseNumber;
    private String status;
}