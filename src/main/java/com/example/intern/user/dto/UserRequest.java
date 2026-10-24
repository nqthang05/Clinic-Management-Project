package com.example.intern.user.dto;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password; // Client gửi password gốc
    private String fullName;
    private String email;
    private String phone;
    private String role;     // ADMIN, DOCTOR, RECEPTIONIST
}