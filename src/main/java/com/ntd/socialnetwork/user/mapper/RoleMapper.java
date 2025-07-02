package com.ntd.socialnetwork.user.mapper;

import com.ntd.socialnetwork.user.dto.request.PermissionRequest;
import com.ntd.socialnetwork.user.dto.request.RoleRequest;
import com.ntd.socialnetwork.user.dto.response.PermissionResponse;
import com.ntd.socialnetwork.user.dto.response.RoleResponse;
import com.ntd.socialnetwork.user.model.Permission;
import com.ntd.socialnetwork.user.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
