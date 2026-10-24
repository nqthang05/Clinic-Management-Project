package com.example.intern.medicine.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MedicineRequest {
    private String name;
    private String activeIngredient;
    private String unit;
    private String description;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
}