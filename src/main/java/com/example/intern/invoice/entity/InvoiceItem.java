package com.example.intern.invoice.entity;

import com.example.intern.medicine.entity.Medicine;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "item_name", nullable = false, length = 150)
    private String itemName;

    @Builder.Default
    @Column(name = "item_type", nullable = false, length = 20)
    private String itemType = "SERVICE"; // SERVICE, MEDICINE, OTHER

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    // Không cần set lineTotal ở Java (hoặc chỉ đánh dấu read-only) vì DB tự sinh.
    @Column(name = "line_total", insertable = false, updatable = false)
    private BigDecimal lineTotal;
}