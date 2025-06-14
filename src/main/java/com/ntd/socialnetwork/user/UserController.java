package com.ntd.socialnetwork.user;

import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<APIResponse<Void>> createUser(
            @RequestBody @Valid UserCreationRequest userCreationRequest) {
        this.userService.createUser(userCreationRequest);

        APIResponse<Void> response = new APIResponse<>(
                true,
                "User created successfully",
                null,
                HttpStatus.CREATED.value()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/users")
    public ResponseEntity<APIResponse<Void>> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        this.userService.updateUser(userUpdateRequest);

        APIResponse<Void> response = new APIResponse<>(
                true,
                "User updated successfully",
                null,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<APIResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = this.userService.findAll();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> {
            log.info("Roles: {}", grantedAuthority.getAuthority());
        });




        APIResponse<List<UserResponse>> response = new APIResponse<>(
                true,
                "Fetched all users",
                users,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<APIResponse<UserResponse>> getUsers(@PathVariable(value = "id") String id) {
        UserResponse user = this.userService.getUserById(id);

        APIResponse<UserResponse> response = new APIResponse<>(
                true,
                "Fetched user by id",
                user,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/myInfo")
    APIResponse<Object> getMyInfo() {
        return APIResponse.builder()
                .success(true)
                .message("My Information")
                .data(this.userService.getMyInfo())
                .code(HttpStatus.OK.value())
                .build();
    }
}
