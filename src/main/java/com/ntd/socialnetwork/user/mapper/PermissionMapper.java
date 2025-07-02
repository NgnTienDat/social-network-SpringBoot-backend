package com.ntd.socialnetwork.user.mapper;

import com.ntd.socialnetwork.user.dto.request.PermissionRequest;
import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.PermissionResponse;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.model.Permission;
import com.ntd.socialnetwork.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
