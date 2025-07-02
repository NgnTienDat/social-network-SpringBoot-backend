package com.ntd.socialnetwork.user.service;

import com.ntd.socialnetwork.user.dto.request.PermissionRequest;
import com.ntd.socialnetwork.user.dto.response.PermissionResponse;
import com.ntd.socialnetwork.user.mapper.PermissionMapper;
import com.ntd.socialnetwork.user.model.Permission;
import com.ntd.socialnetwork.user.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toList());
    }

    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
