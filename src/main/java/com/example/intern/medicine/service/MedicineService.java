package com.example.intern.medicine.service;

import com.example.intern.medicine.dto.MedicineRequest;
import com.example.intern.medicine.dto.MedicineResponse;
import com.example.intern.medicine.entity.Medicine;
import com.example.intern.medicine.mapper.MedicineMapper;
import com.example.intern.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    public MedicineResponse createMedicine(MedicineRequest request) {
        Medicine medicine = medicineMapper.toEntity(request);
        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }

    public List<MedicineResponse> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(medicineMapper::toResponse)
                .collect(Collectors.toList());
    }

    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Thuốc ID: " + id));
        return medicineMapper.toResponse(medicine);
    }
}