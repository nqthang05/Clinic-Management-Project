package com.example.intern.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int currentPage;    // Trang hiện tại (bắt đầu từ 0)
    private int totalPages;     // Tổng số trang
    private long totalElements; // Tổng số bản ghi trong Database
    private int pageSize;       // Số bản ghi trên 1 trang
    private List<T> content;    // Dữ liệu thực tế (List Bệnh nhân, Hóa đơn...)
}