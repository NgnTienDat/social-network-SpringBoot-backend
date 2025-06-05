package com.ntd.socialnetwork.user;

import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.APIResponse;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.model.User;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<APIResponse<Void>> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
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
    public ResponseEntity<APIResponse<List<User>>> getAllUsers() {
        List<User> users = this.userService.findAll();

        APIResponse<List<User>> response = new APIResponse<>(
                true,
                "Fetched all users",
                users,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<APIResponse<UserResponse>> getUsers(@PathVariable(value = "id") String id) {
        UserResponse user = this.userService.getUserByUsername(id);

        APIResponse<UserResponse> response = new APIResponse<>(
                true,
                "Fetched user by id",
                user,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
