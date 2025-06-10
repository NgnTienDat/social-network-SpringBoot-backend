package com.ntd.socialnetwork.user.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

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
    Set<String> roles;

}
