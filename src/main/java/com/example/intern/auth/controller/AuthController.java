package com.example.intern.auth.controller;

import com.example.intern.auth.dto.AuthRequest;
import com.example.intern.auth.dto.AuthResponse;
import com.example.intern.auth.service.AuthService;
import com.example.intern.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .result(authService.login(request))
                .build();
    }
}