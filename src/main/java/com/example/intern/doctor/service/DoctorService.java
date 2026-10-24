package com.example.intern.doctor.service;

import com.example.intern.doctor.dto.DoctorRequest;
import com.example.intern.doctor.dto.DoctorResponse;
import com.example.intern.doctor.entity.Doctor;
import com.example.intern.doctor.mapper.DoctorMapper;
import com.example.intern.doctor.repository.DoctorRepository;
import com.example.intern.exception.AppException;
import com.example.intern.exception.ErrorCode;
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
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponse createDoctor(DoctorRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!"DOCTOR".equals(user.getRole())) throw new AppException(ErrorCode.ROLE_INVALID);
        if (doctorRepository.existsByUserId(request.getUserId())) throw new AppException(ErrorCode.DOCTOR_EXISTED);
        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) throw new AppException(ErrorCode.LICENSE_EXISTED);

        Doctor doctor = doctorMapper.toEntity(request, user);
        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(doctorMapper::toResponse).collect(Collectors.toList());
    }
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        // Kiểm tra trùng License Number
        if (!doctor.getLicenseNumber().equals(request.getLicenseNumber())
                && doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new AppException(ErrorCode.LICENSE_EXISTED);
        }

        doctor.setFullName(request.getFullName());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setLicenseNumber(request.getLicenseNumber());

        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    // Xóa mềm Bác sĩ
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        doctor.setStatus("INACTIVE");
        doctorRepository.save(doctor);
    }
}