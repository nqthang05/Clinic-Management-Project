package com.example.intern.statistic.service;

import com.example.intern.appointment.repository.AppointmentRepository;
import com.example.intern.invoice.repository.InvoiceRepository;
import com.example.intern.medicine.mapper.MedicineMapper;
import com.example.intern.medicine.repository.MedicineRepository;
import com.example.intern.patient.repository.PatientRepository;
import com.example.intern.statistic.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    public DashboardResponse getDashboardData() {
        // 1. Tính doanh thu (Nếu chưa có hóa đơn nào PAID thì sum sẽ ra null, cần gán bằng 0)
        BigDecimal revenue = invoiceRepository.calculateTotalRevenue();
        if (revenue == null) {
            revenue = BigDecimal.ZERO;
        }

        // 2. Đếm số lượng
        long patients = patientRepository.count();
        long appointments = appointmentRepository.countTodayAppointments();

        // 3. Lấy danh sách thuốc sắp hết (Cảnh báo khi tồn kho <= 20)
        var lowStock = medicineRepository.findByStockQuantityLessThanEqual(20).stream()
                .map(medicineMapper::toResponse)
                .collect(Collectors.toList());

        // 4. Trả về DTO tổng hợp
        return DashboardResponse.builder()
                .totalRevenue(revenue)
                .totalPatients(patients)
                .todayAppointments(appointments)
                .lowStockMedicines(lowStock)
                .build();
    }
}