package com.ntd.socialnetwork.user.controller;


import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.user.dto.request.RoleRequest;
import com.ntd.socialnetwork.user.dto.response.RoleResponse;
import com.ntd.socialnetwork.user.service.RoleService;
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
public class RoleController {
   RoleService roleService;


    @PostMapping("/roles")
    public APIResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest roleRequest) {
        return APIResponse.<RoleResponse>builder()
                .message("Role created")
                .data(roleService.createRole(roleRequest))
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/roles")
    public APIResponse<List<RoleResponse>> getRoles() {
        return APIResponse.<List<RoleResponse>>builder()
                .message("Get all roles")
                .data(roleService.getAll())
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/roles")
    public APIResponse<Void> deleteRoles(@RequestBody @Valid RoleRequest roleRequest) {
        this.roleService.deleteRole(roleRequest);
        return APIResponse.<Void>builder()
                .message("Role deleted")
                .code(HttpStatus.OK.value())
                .build();
    }
}
