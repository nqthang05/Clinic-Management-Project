package com.example.intern.patient.mapper;



import com.example.intern.patient.dto.PatientRequest;
import com.example.intern.patient.dto.PatientResponse;
import com.example.intern.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request) {
        return Patient.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .medicalHistory(request.getMedicalHistory())
                .allergies(request.getAllergies())
                .build();
    }

    public PatientResponse toResponse(Patient entity) {
        return PatientResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .build();
    }
}
