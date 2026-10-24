package com.example.intern.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Nếu result null thì sẽ không hiển thị key "result" trên JSON
public class ApiResponse<T> {

    @Builder.Default
    private int code = 1000; // Mặc định 1000 là thành công

    @Builder.Default
    private String message = "Success";

    private T result;
}