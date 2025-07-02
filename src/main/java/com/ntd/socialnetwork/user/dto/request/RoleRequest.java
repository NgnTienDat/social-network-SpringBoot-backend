package com.ntd.socialnetwork.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleRequest {

    @NotBlank(message = "NOT_BLANk")
    String name;
    String description;
    Set<String> permissions;
}
