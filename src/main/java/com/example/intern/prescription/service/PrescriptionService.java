package com.example.intern.prescription.service;

import com.example.intern.medicalrecord.entity.MedicalRecord;
import com.example.intern.medicalrecord.repository.MedicalRecordRepository;
import com.example.intern.medicine.entity.Medicine;
import com.example.intern.medicine.repository.MedicineRepository;
import com.example.intern.prescription.dto.PrescriptionRequest;
import com.example.intern.prescription.dto.PrescriptionResponse;
import com.example.intern.prescription.entity.Prescription;
import com.example.intern.prescription.entity.PrescriptionItem;
import com.example.intern.prescription.mapper.PrescriptionMapper;
import com.example.intern.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Transactional
    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        MedicalRecord record = medicalRecordRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Medical Record ID"));

        Prescription prescription = Prescription.builder()
                .medicalRecord(record)
                .notes(request.getNotes())
                .build();

        for (PrescriptionRequest.PrescriptionItemRequest itemReq : request.getItems()) {
            Medicine medicine = medicineRepository.findById(itemReq.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Thuốc ID: " + itemReq.getMedicineId()));

            if (medicine.getStockQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Thuốc " + medicine.getName() + " không đủ số lượng trong kho!");
            }

            // Trừ số lượng tồn kho
            medicine.setStockQuantity(medicine.getStockQuantity() - itemReq.getQuantity());
            medicineRepository.save(medicine);

            PrescriptionItem item = PrescriptionItem.builder()
                    .prescription(prescription)
                    .medicine(medicine)
                    .dosage(itemReq.getDosage())
                    .frequency(itemReq.getFrequency())
                    .durationDays(itemReq.getDurationDays())
                    .quantity(itemReq.getQuantity())
                    .instructions(itemReq.getInstructions())
                    .build();

            prescription.getItems().add(item);
        }

        return prescriptionMapper.toResponse(prescriptionRepository.save(prescription));
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }
}