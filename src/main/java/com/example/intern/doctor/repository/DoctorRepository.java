package com.example.intern.doctor.repository;

import com.example.intern.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByUserId(Long userId);
    boolean existsByLicenseNumber(String licenseNumber);
}