package com.example.DATN.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotNull(message = "Thông tin bắt buộc")
    @Size(min = 1,message = "Độ dài tối thiểu là 1")
    private String username;
    @NotNull(message = "Thông tin bắt buộc")
    @Size(min = 8,message = "Độ dài tối thiểu là 8")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!]).*$",
            message = "Mật khẩu phải chứa ít nhất một chữ số và một ký tự đặc biệt")
    private String password;
    @NotNull(message = "Thông tin bắt buộc")
    private String fullName;
    @NotNull(message = "Thông tin bắt buộc")
    private String sdt;
    @NotNull(message = "Thông tin bắt buộc")
    private String diaChi;
    @NotNull(message = "Thông tin bắt buộc")
    @Email(message = "Email không hợp lệ")
    private String email;

}
