package com.example.intern.user.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "Password không được để trống")
    private String password; // Client gửi password gốc
    @NotBlank(message = "FullName không được để trống")
    private String fullName;
    private String email;
    private String phone;
    private String role;     // ADMIN, DOCTOR, RECEPTIONIST
}