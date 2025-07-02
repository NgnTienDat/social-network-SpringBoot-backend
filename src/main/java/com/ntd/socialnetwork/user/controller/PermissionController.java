package com.ntd.socialnetwork.user.controller;


import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.user.dto.request.PermissionRequest;
import com.ntd.socialnetwork.user.dto.response.PermissionResponse;
import com.ntd.socialnetwork.user.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/permissions")
    public APIResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest permissionRequest) {
        return APIResponse.<PermissionResponse>builder()
                .message("Permission created")
                .data(permissionService.createPermission(permissionRequest))
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/permissions")
    public APIResponse<List<PermissionResponse>> getPermissions() {
        return APIResponse.<List<PermissionResponse>>builder()
                .message("Get all permissions")
                .data(permissionService.getAllPermissions())
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/permissions/{permission}")
    public APIResponse<Void> deletePermission(@PathVariable("permission") String permission) {
        this.permissionService.deletePermission(permission);
        return APIResponse.<Void>builder()
                .message("Permission deleted")
                .code(HttpStatus.OK.value())
                .build();
    }
}
