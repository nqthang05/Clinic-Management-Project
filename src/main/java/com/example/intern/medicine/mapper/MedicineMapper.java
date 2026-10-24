package com.example.intern.medicine.mapper;

import com.example.intern.medicine.dto.MedicineRequest;
import com.example.intern.medicine.dto.MedicineResponse;
import com.example.intern.medicine.entity.Medicine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MedicineMapper {

    public Medicine toEntity(MedicineRequest request) {
        return Medicine.builder()
                .name(request.getName())
                .activeIngredient(request.getActiveIngredient())
                .unit(request.getUnit())
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice() != null ? request.getUnitPrice() : BigDecimal.ZERO)
                .stockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0)
                .build();
    }

    public MedicineResponse toResponse(Medicine entity) {
        return MedicineResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .activeIngredient(entity.getActiveIngredient())
                .unit(entity.getUnit())
                .description(entity.getDescription())
                .unitPrice(entity.getUnitPrice())
                .stockQuantity(entity.getStockQuantity())
                .status(entity.getStatus())
                .build();
    }
}