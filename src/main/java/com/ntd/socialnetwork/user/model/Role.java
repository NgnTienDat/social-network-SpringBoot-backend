package com.ntd.socialnetwork.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role {
    @Id
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Permission> permissions;
}
