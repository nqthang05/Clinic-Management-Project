package com.example.intern.doctor.mapper;

import com.example.intern.doctor.dto.DoctorRequest;
import com.example.intern.doctor.dto.DoctorResponse;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public Doctor toEntity(DoctorRequest request, User user) {
        return Doctor.builder()
                .user(user)
                .fullName(request.getFullName())
                .specialty(request.getSpecialty())
                .phone(request.getPhone())
                .email(request.getEmail())
                .licenseNumber(request.getLicenseNumber())
                .build();
    }

    public DoctorResponse toResponse(Doctor entity) {
        return DoctorResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .fullName(entity.getFullName())
                .specialty(entity.getSpecialty())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .licenseNumber(entity.getLicenseNumber())
                .status(entity.getStatus())
                .build();
    }
}