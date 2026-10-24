package com.example.intern.patient.service;


import com.example.intern.patient.dto.PatientRequest;
import com.example.intern.patient.dto.PatientResponse;
import com.example.intern.patient.entity.Patient;
import com.example.intern.patient.mapper.PatientMapper;
import com.example.intern.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(savedPatient);
    }

    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));
        return patientMapper.toResponse(patient);
    }
}