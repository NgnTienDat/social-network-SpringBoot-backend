package com.ntd.socialnetwork.user.dto.response;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
//    String id;
    String username;
    String firstname;
    String lastname;
    Byte gender;
    String avatar;
    Instant dateOfBirth;
}
