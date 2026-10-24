package com.example.intern.user.mapper;

import com.example.intern.user.dto.UserRequest;
import com.example.intern.user.dto.UserResponse;
import com.example.intern.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                // TODO: Thực tế sẽ dùng BCryptPasswordEncoder để mã hóa password ở Service
                .passwordHash(request.getPassword())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole() != null ? request.getRole() : "RECEPTIONIST")
                .build();
    }

    public UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .status(entity.getStatus())
                .build(); // Cố tình không trả về passwordHash để bảo mật
    }
}