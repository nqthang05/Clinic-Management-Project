package com.example.intern.patient.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender; // MALE, FEMALE, OTHER
    private String phone;
    private String email;
    private String address;
    private String medicalHistory;
    private String allergies;
}