package com.ntd.socialnetwork.user.service;

import com.ntd.socialnetwork.common.exception.AppException;
import com.ntd.socialnetwork.user.repository.RoleRepository;
import com.ntd.socialnetwork.user.repository.UserRepository;
import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.enums.Role;
import com.ntd.socialnetwork.common.exception.ErrorCode;
import com.ntd.socialnetwork.user.mapper.UserMapper;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public void createUser(UserCreationRequest userCreationRequest) {

            // * field username is unique, so function don't need to check username existed anymore

//        if (this.userRepository.existsUserByUsername(userCreationRequest.getUsername())) {
//            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
//        }

        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);
        try {

            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {

        User user = this.userRepository.findByUsername(userUpdateRequest.getUsername())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

//        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
//        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
//        user.setRoles(new HashSet<>(roles));


//        userMapper.updateUser(user, userUpdateRequest);
        userRepository.save(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = this.userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        return userMapper.toUserResponse(user);
    }

//    @PreAuthorize("hasAuthority('APPROVE_POST')") phan quyen theo permission
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND)));
    }

    // using for AppInit
    public User getUserByUsername(String username) {
         User user = this.userRepository.findByUsername(username)
                 .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

         return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(String id) {
        return this.findUserById(id);
    }
}
