package com.ntd.socialnetwork.user.mapper;

import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    //Bo qua cac truong null khi update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    UserResponse toUserResponse(User user);

}
