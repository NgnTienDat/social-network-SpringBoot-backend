package com.ntd.socialnetwork.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    String id;

    @Size(max = 45, message = "Username không được quá 45 ký tự!")
    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "username", nullable = false, length = 45, unique = true)
    String username;

    @Size(max = 255, min = 3, message = "Mật khẩu tối thiểu 3 và tối đa 16 ký tự!")
    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Column(name = "firstname", nullable = false, length = 45)
    String firstname;

    @Column(name = "lastname", nullable = false, length = 45)
    String lastname;

    @Column(name = "email", nullable = false, length = 45)
    String email;

    @Column(name = "phone", nullable = false, length = 11)
    String phone;

    @Column(name = "gender")
    Byte gender;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "date_of_birth")
    Instant dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "roles", length = 100)
    Set<Role> roles;




}
