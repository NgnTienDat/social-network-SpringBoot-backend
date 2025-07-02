package com.ntd.socialnetwork.user.service;

import com.ntd.socialnetwork.user.dto.request.PermissionRequest;
import com.ntd.socialnetwork.user.dto.request.RoleRequest;
import com.ntd.socialnetwork.user.dto.response.PermissionResponse;
import com.ntd.socialnetwork.user.dto.response.RoleResponse;
import com.ntd.socialnetwork.user.mapper.PermissionMapper;
import com.ntd.socialnetwork.user.mapper.RoleMapper;
import com.ntd.socialnetwork.user.model.Permission;
import com.ntd.socialnetwork.user.repository.PermissionRepository;
import com.ntd.socialnetwork.user.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    public void deleteRole(RoleRequest roleRequest) {
        this.roleRepository.deleteById(roleRequest.getName());
    }
}
