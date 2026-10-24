package com.example.intern.auth.service;

import com.example.intern.auth.dto.AuthRequest;
import com.example.intern.auth.dto.AuthResponse;
import com.example.intern.exception.AppException;
import com.example.intern.exception.ErrorCode;
import com.example.intern.security.JwtUtils;
import com.example.intern.user.entity.User;
import com.example.intern.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse login(AuthRequest request) {
        // Tìm user theo username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.WRONG_CREDENTIALS));

        // Kiểm tra mật khẩu có khớp mã BCrypt không
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.WRONG_CREDENTIALS);
        }

        // Sinh token
        String jwtToken = jwtUtils.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}