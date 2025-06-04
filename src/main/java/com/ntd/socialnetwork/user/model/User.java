package com.ntd.socialnetwork.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Size(max = 45, message = "Username không được quá 45 ký tự!")
    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Size(max = 255, min = 3, message = "Mật khẩu tối thiểu 3 và tối đa 16 ký tự!")
    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "firstname", nullable = false, length = 45)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;


}
