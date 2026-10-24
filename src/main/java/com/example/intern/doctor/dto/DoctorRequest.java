package com.example.intern.doctor.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorRequest {
    @NotNull(message = "User ID không được để trống")
    private Long userId; // Liên kết với tài khoản User

    @NotBlank(message = "Họ tên bác sĩ không được để trống")
    private String fullName;

    @NotBlank(message = "Chuyên khoa không được để trống")
    private String specialty;

    private String phone;
    private String email;

    @NotBlank(message = "Số giấy phép chứng chỉ hành nghề không được để trống")
    private String licenseNumber;
}