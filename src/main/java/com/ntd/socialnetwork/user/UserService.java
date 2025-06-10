package com.ntd.socialnetwork.user;

import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.enums.Role;
import com.ntd.socialnetwork.user.exception.ErrorCode;
import com.ntd.socialnetwork.user.exception.UserNotFoundException;
import com.ntd.socialnetwork.user.exception.UsernameAlreadyExistsException;
import com.ntd.socialnetwork.user.mapper.UserMapper;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public void createUser(UserCreationRequest userCreationRequest) {

        if (this.userRepository.existsUserByUsername(userCreationRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        userRepository.save(user);
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {

        User user = this.userRepository.findByUsername(userUpdateRequest.getUsername());

        if (user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOTFOUND);
        }

        userMapper.updateUser(user, userUpdateRequest);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOTFOUND)));
    }

    public User getUserByUsername(String username) {
         User user = this.userRepository.findByUsername(username);

         return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
