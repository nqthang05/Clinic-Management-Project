package com.example.intern.medicine.repository;

import com.example.intern.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByStockQuantityLessThanEqual(Integer quantity);
}