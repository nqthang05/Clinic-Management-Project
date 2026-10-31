package com.example.intern.invoice.repository;

import com.example.intern.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsByAppointmentId(Long appointmentId);
    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.status = 'PAID'")
    BigDecimal calculateTotalRevenue();
}