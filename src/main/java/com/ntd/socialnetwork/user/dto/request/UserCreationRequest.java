package com.ntd.socialnetwork.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserCreationRequest {

    @Size(max = 20, min = 8, message = "USERNAME_INVALID")
    @NotBlank(message = "NOT_BLANk")
    String username;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&:()])[A-Za-z\\d@$!%*?&:()]{8,}$",
            message = "INVALID_PASSWORD"
    )
    @NotBlank(message = "NOT_BLANK")
    String password;


    @Size(max = 45, message = "Họ không được quá 45 ký tự!")
    @NotBlank(message = "NOT_BLANK")
    String firstname;

    @NotBlank(message = "NOT_BLANK")
    @Size(max = 45, message = "Tên không được quá 45 ký tự!")
    String lastname;

    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "NOT_BLANK")
    @Size(max = 100, message = "Email không được quá 100 ký tự!")
    String email;

    @NotBlank(message = "NOT_BLANK")
    @Pattern(regexp = "^(\\+84|0)(3|5|7|8|9)[0-9]{8}$", message = "INVALID_PHONE")
    String phone;

    @Min(value = 0, message = "Giới tính không hợp lệ!") // 0: nam, 1: nữ, 2: khác (ví dụ)
    @Max(value = 2, message = "Giới tính không hợp lệ!")
    Byte gender;

    @Size(max = 255, message = "Đường dẫn avatar không được quá 255 ký tự!")
    String avatar;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    Instant dateOfBirth;
}
