package com.example.intern.appointment.repository;

import com.example.intern.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Kiểm tra xem Bác sĩ đã có lịch trùng vào giờ này chưa
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.appointmentTime) = CURRENT_DATE")
    long countTodayAppointments();
}