package com.example.intern.statistic.controller;

import com.example.intern.common.ApiResponse;
import com.example.intern.statistic.dto.DashboardResponse;
import com.example.intern.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    // Chỉ Lễ tân và Admin được xem báo cáo
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> getDashboardData() {
        return ApiResponse.<DashboardResponse>builder()
                .result(statisticService.getDashboardData())
                .build();
    }
}