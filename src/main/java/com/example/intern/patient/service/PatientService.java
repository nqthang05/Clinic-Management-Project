package com.example.intern.patient.service;

import com.example.intern.exception.AppException;
import com.example.intern.exception.ErrorCode;
import com.example.intern.patient.dto.PatientRequest;
import com.example.intern.patient.dto.PatientResponse;
import com.example.intern.patient.entity.Patient;
import com.example.intern.patient.mapper.PatientMapper;
import com.example.intern.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.intern.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    public PageResponse<PatientResponse> getPatients(String keyword, int page, int size) {
        // Mặc định sắp xếp theo ngày tạo mới nhất đưa lên đầu
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Patient> patientPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            patientPage = patientRepository.searchPatients(keyword.trim(), pageable);
        } else {
            patientPage = patientRepository.findAll(pageable);
        }

        // Chuyển đổi từ Entity sang DTO
        List<PatientResponse> content = patientPage.getContent().stream()
                .map(patientMapper::toResponse)
                .collect(Collectors.toList());

        // Đóng gói vào PageResponse
        return PageResponse.<PatientResponse>builder()
                .currentPage(patientPage.getNumber())
                .totalPages(patientPage.getTotalPages())
                .totalElements(patientPage.getTotalElements())
                .pageSize(patientPage.getSize())
                .content(content)
                .build();
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));
        return patientMapper.toResponse(patient);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));

        // Cập nhật các trường
        patient.setFullName(request.getFullName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setAllergies(request.getAllergies());

        return patientMapper.toResponse(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new AppException(ErrorCode.PATIENT_NOT_FOUND);
        }
        try {
            patientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Bắt lỗi khóa ngoại nếu bệnh nhân đã có lịch hẹn/hồ sơ khám
            throw new AppException(ErrorCode.DATA_IN_USE);
        }
    }
}