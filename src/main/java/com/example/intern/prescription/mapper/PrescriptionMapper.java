package com.example.intern.prescription.mapper;

import com.example.intern.prescription.dto.PrescriptionResponse;
import com.example.intern.prescription.entity.Prescription;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PrescriptionMapper {
    public PrescriptionResponse toResponse(Prescription entity) {
        return PrescriptionResponse.builder()
                .id(entity.getId())
                .medicalRecordId(entity.getMedicalRecord().getId())
                .notes(entity.getNotes())
                .prescribedDate(entity.getCreatedAt())
                .items(entity.getItems().stream().map(item ->
                        PrescriptionResponse.PrescriptionItemResponse.builder()
                                .id(item.getId())
                                .medicineId(item.getMedicine().getId())
                                .medicineName(item.getMedicine().getName())
                                .dosage(item.getDosage())
                                .frequency(item.getFrequency())
                                .quantity(item.getQuantity())
                                .instructions(item.getInstructions())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}