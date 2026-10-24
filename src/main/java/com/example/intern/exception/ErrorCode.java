package com.example.intern.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 10xx: Lỗi hệ thống cơ bản
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(1001, "Lỗi xác thực dữ liệu", HttpStatus.BAD_REQUEST),

    // 20xx: Lỗi liên quan đến User
    USER_EXISTED(2001, "Username đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2002, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    ROLE_INVALID(2003, "Tài khoản không có quyền thực hiện hành động này", HttpStatus.FORBIDDEN),

    // 30xx: Lỗi liên quan nghiệp vụ Clinic
    PATIENT_NOT_FOUND(3001, "Không tìm thấy hồ sơ bệnh nhân", HttpStatus.NOT_FOUND),
    DOCTOR_NOT_FOUND(3002, "Không tìm thấy bác sĩ", HttpStatus.NOT_FOUND),
    DOCTOR_EXISTED(3003, "User này đã được liên kết với một hồ sơ Bác sĩ khác", HttpStatus.BAD_REQUEST),
    LICENSE_EXISTED(3004, "Số giấy phép hành nghề đã tồn tại", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(3005, "Không tìm thấy lịch hẹn", HttpStatus.NOT_FOUND),
    APPOINTMENT_CONFLICT(3006, "Bác sĩ đã có lịch hẹn vào thời gian này", HttpStatus.BAD_REQUEST),
    MEDICAL_RECORD_NOT_FOUND(3007, "Không tìm thấy hồ sơ khám bệnh", HttpStatus.NOT_FOUND),
    RECORD_EXISTED(3008, "Lịch hẹn này đã có hồ sơ khám bệnh", HttpStatus.BAD_REQUEST),
    MEDICINE_NOT_FOUND(3009, "Không tìm thấy thuốc", HttpStatus.NOT_FOUND),
    MEDICINE_OUT_OF_STOCK(3010, "Thuốc không đủ số lượng trong kho", HttpStatus.BAD_REQUEST),
    INVOICE_EXISTED(3011, "Lịch hẹn này đã được xuất hóa đơn", HttpStatus.BAD_REQUEST),
    // 40xx: Lỗi liên quan đến Database/Ràng buộc
    DATA_IN_USE(4001, "Dữ liệu đang được sử dụng ở nơi khác, không thể xóa cứng", HttpStatus.CONFLICT),
    PRESCRIPTION_NOT_FOUND(3012, "Không tìm thấy đơn thuốc", HttpStatus.NOT_FOUND),
    INVOICE_NOT_FOUND(3013, "Không tìm thấy hóa đơn", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}