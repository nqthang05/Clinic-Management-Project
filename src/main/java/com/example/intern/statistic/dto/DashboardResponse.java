package com.example.intern.statistic.dto;

import com.example.intern.medicine.dto.MedicineResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private BigDecimal totalRevenue;         // Tổng doanh thu
    private long totalPatients;              // Tổng số bệnh nhân
    private long todayAppointments;          // Số lịch hẹn hôm nay
    private List<MedicineResponse> lowStockMedicines; // Danh sách thuốc sắp hết
}