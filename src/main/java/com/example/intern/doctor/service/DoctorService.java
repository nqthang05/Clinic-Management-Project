package com.example.intern.doctor.service;

import com.example.intern.doctor.dto.DoctorRequest;
import com.example.intern.doctor.dto.DoctorResponse;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.doctor.mapper.DoctorMapper;
import com.example.intern.doctor.repository.DoctorRepository;
import com.example.intern.user.entity.User;
import com.example.intern.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository; // Inject UserRepository để lấy User
    private final DoctorMapper doctorMapper;

    public DoctorResponse createDoctor(DoctorRequest request) {
        // 1. Kiểm tra User tồn tại không & Role có phải DOCTOR không
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với ID: " + request.getUserId()));

        if (!"DOCTOR".equals(user.getRole())) {
            throw new RuntimeException("Tài khoản này không có quyền DOCTOR");
        }

        // 2. Kiểm tra User này đã làm bác sĩ chưa
        if (doctorRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("User này đã được liên kết với một hồ sơ Bác sĩ khác");
        }

        // 3. Kiểm tra trùng License (Giấy phép)
        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new RuntimeException("Số giấy phép hành nghề đã tồn tại");
        }

        Doctor doctor = doctorMapper.toEntity(request, user);
        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }
}