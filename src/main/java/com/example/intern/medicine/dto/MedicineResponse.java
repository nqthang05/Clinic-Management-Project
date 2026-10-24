package com.example.intern.medicine.dto;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class MedicineResponse {
    private Long id;
    private String name;
    private String activeIngredient;
    private String unit;
    private String description;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private String status;
}